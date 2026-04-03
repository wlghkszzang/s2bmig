/**
 * 표준분류-전시카테고리 연결 관리 자바스크립트 컨트롤러
 */
var StdDispCategoryConnect = {
    gridId: "connectGrid",
    myGrid: null,
    zTreeObj: null,
    currentStdCtgNo: null,

    init: function () {
        // jQuery 3.x compatibility patch for older zTree versions
        if (window.$ && !$.browser) {
            $.browser = { msie: false, version: 0 };
        }

        this.createGrid();
        this.loadTree();
        this.bindEvents();
    },

    createGrid: function () {
        var _this = this;
        // AS-IS 달성 항목: siteNm, dpmlNm, dispCtgNo, dispCtgNm, repCtgYn(편집가능, Y/N), useYn, sysModId, sysModDtm
        var columnLayout = [
            { dataField: "stdCtgNo", visible: false },
            { dataField: "siteNo", visible: false },
            { dataField: "siteNm", headerText: "사이트", width: 100, editable: false, style: "aui-disable-cell" },
            { dataField: "dpmlNo", visible: false },
            { dataField: "dpmlNm", headerText: "통합몰", width: 100, editable: false, style: "aui-disable-cell" },
            { dataField: "dispCtgNo", headerText: "전시카테고리 번호", width: 120, editable: false, style: "aui-disable-cell" },
            { dataField: "dispCtgNm", headerText: "전시카테고리 명", width: 250, style: "left-text aui-disable-cell", editable: false },
            {
                dataField: "repCtgYn", headerText: "대표 여부 *", width: 100,
                renderer: {
                    type: "CheckBoxEditRenderer",
                    editable: true,
                    checkValue: "Y",
                    unCheckValue: "N"
                }
            },
            {
                dataField: "useYn", headerText: "전시 여부 *", width: 100, editable: false,
                renderer: {
                    type: "CheckBoxEditRenderer",
                    checkValue: "Y",
                    unCheckValue: "N"
                }
            },
            { dataField: "sysModId", headerText: "수정자", width: 100, editable: false, style: "aui-disable-cell" },
            { dataField: "sysModDtm", headerText: "수정일시", width: 120, editable: false, style: "aui-disable-cell" }
        ];

        var gridProps = {
            editable: true,
            headerHeight: 40,
            rowHeight: 40,
            selectionMode: "multipleRows",
            showRowNumColumn: true,
            showRowCheckColumn: true,
            noDataMessage: "연결할 대상 분류를 좌측 트리에서 먼저 선택해주세요."
        };

        this.myGrid = AUIGrid.create("#" + this.gridId, columnLayout, gridProps);

        // AS-IS 누락 로직: 특정 통합몰의 대표여부(repCtgYn)를 'Y'로 체크 시 다른 대상은 'N'으로 자동 변경
        AUIGrid.bind(this.myGrid, "cellEditEnd", function (event) {
            if (event.dataField === "repCtgYn" && event.value === "Y") {
                if (confirm("대표카테고리 여부를 변경 하시겠습니까?\n변경 시 기존 대표카테고리는 일반으로 변경됩니다.")) {
                    var dpmlNo = event.item.dpmlNo;
                    var gridItems = AUIGrid.getGridData(_this.myGrid);
                    for (var i = 0; i < gridItems.length; i++) {
                        if (i !== event.rowIndex && gridItems[i].dpmlNo === dpmlNo) {
                            AUIGrid.updateRow(_this.myGrid, { repCtgYn: "N" }, i);
                        }
                    }
                } else {
                    // 사용자 취소 시 롤백
                    setTimeout(function () {
                        AUIGrid.updateRow(_this.myGrid, { repCtgYn: "N" }, event.rowIndex);
                    }, 10);
                }
            }
        });
    },

    loadTree: function () {
        var _this = this;
        var treeSetting = {
            view: {
                showLine: true,
                selectedMulti: false,
                fontCss: function (treeId, treeNode) {
                    return treeNode.leafCtgYn === 'N' ? { 'color': '#9ca3af' } : { 'color': '#374151' };
                }
            },
            data: {
                simpleData: { enable: true, idKey: "stdCtgNo", pIdKey: "uprStdCtgNo", rootPId: null },
                key: { name: "stdCtgNm" }
            },
            callback: {
                onClick: function (event, treeId, treeNode) {
                    if (treeNode.leafCtgYn !== 'Y') {
                        return false;
                    }
                    _this.currentStdCtgNo = treeNode.stdCtgNo;
                    _this.loadConnectGrid(treeNode.stdCtgNo);
                }
            }
        };

        axios.get("/api/v1/display/standardCategoryMgmt/getStandardCategoryMgmt.do")
            .then(function (res) {
                if (res.data && res.data.success && res.data.data) {
                    var treeData = res.data.data.map(function (item) {
                        return {
                            ...item,
                            stdCtgNo: String(item.stdCtgNo),
                            uprStdCtgNo: (item.uprStdCtgNo && item.uprStdCtgNo !== '0') ? String(item.uprStdCtgNo) : null,
                            isParent: (item.leafCtgYn === "N"),
                            open: false
                        };
                    });
                    var $treeTarget = $("#stdCategoryTree");
                    if ($treeTarget.length > 0) {
                        _this.zTreeObj = $.fn.zTree.init($treeTarget, treeSetting, treeData);
                    }
                }
            })
            .catch(function (err) {
                console.error("Tree loading error:", err);
            });
    },

    loadConnectGrid: function (stdCtgNo) {
        var _this = this;
        AUIGrid.showAjaxLoader(this.myGrid);

        axios.get("/api/v1/display/standardDisplayCategoryConnect/getStandardDisplayCategoryConnect.do", {
            params: { stdCtgNo: stdCtgNo }
        }).then(function (res) {
            AUIGrid.removeAjaxLoader(_this.myGrid);
            if (res.data && res.data.success) {
                AUIGrid.setGridData(_this.myGrid, res.data.data || []);
            }
        }).catch(function (err) {
            AUIGrid.removeAjaxLoader(_this.myGrid);
            console.error("Grid loading error:", err);
        });
    },

    addRow: function () {
        if (!this.currentStdCtgNo) {
            alert("표준분류 리프행위치가 아닙니다.");
            return;
        }

        // AS-IS 누락 로직: 멀티 선택(argSelectType=2) 및 실시간 전시 상태(argUseYn=Y) 파라미터 전달
        var url = "/popup/displayCategoryMgmtPopup.displayCategoryListPopup.do?argSelectType=2&argUseYn=Y";
        var winWidth = 1000;
        var winHeight = 700;
        var winLeft = (screen.width - winWidth) / 2;
        var winTop = (screen.height - winHeight) / 2;
        window.open(url, "displayCategoryListPopup", "width=" + winWidth + ",height=" + winHeight + ",left=" + winLeft + ",top=" + winTop + ",scrollbars=yes");
    },

    onPopupApply: function (selectedItems) {
        var _this = this;
        var gridRows = AUIGrid.getGridData(this.myGrid);
        var existingCtgNos = gridRows.map(function (row) { return row.dispCtgNo; });

        var newRows = [];
        selectedItems.forEach(function (item) {
            if (existingCtgNos.indexOf(item.dispCtgNo) === -1) {
                // AS-IS 누락 로직: 팝업에서 전달된 hierarchyNm(계층경로)을 우선 사용
                var dispCtgNm = item.hierarchyNm || item.dispCtgNm;

                var newItem = {
                    stdCtgNo: _this.currentStdCtgNo,
                    siteNo: item.siteNo,
                    siteNm: item.siteNm,
                    dpmlNo: item.dpmlNo,
                    dpmlNm: item.dpmlNm,
                    dispCtgNo: item.dispCtgNo,
                    dispCtgNm: dispCtgNm,
                    useYn: "N", // 추가 시 기본값 N
                    repCtgYn: "N" // 추가 시 기본값 N
                };
                newRows.push(newItem);
            }
        });

        if (newRows.length !== selectedItems.length) {
            alert("존재하는 전시카테고리번호 입니다.");
        }

        if (newRows.length > 0) {
            AUIGrid.addRow(_this.myGrid, newRows, "last");
        }
    },

    removeRow: function () {
        var checkedItems = AUIGrid.getCheckedRowItems(this.myGrid);
        if (checkedItems.length === 0) {
            alert("선택된 내용이 없습니다.");
            return;
        }

        // AS-IS 누락 로직: 대표카테고리는 삭제 불가
        for (var i = 0; i < checkedItems.length; i++) {
            if (checkedItems[i].item.repCtgYn === 'Y') {
                alert("대표 카테고리는 삭제할 수 없습니다.");
                return;
            }
        }

        AUIGrid.removeCheckedRows(this.myGrid);
    },

    saveGridData: function () {
        if (!this.currentStdCtgNo) return;

        // 선택된 행이 최소 한 개 있어야만 진행 (AS-IS는 체크된 로우가 존재하는지 검증함)
        var checkedItems = AUIGrid.getCheckedRowItems(this.myGrid);
        if (checkedItems.length === 0) {
            alert("선택된 내용이 없습니다.");
            return;
        }

        var addedItems = AUIGrid.getAddedRowItems(this.myGrid);
        var updatedItems = AUIGrid.getEditedRowItems(this.myGrid);
        var removedItems = AUIGrid.getRemovedItems(this.myGrid);

        if (addedItems.length === 0 && updatedItems.length === 0 && removedItems.length === 0) {
            alert("변경된 데이터가 없습니다.");
            return;
        }

        // AS-IS 누락 로직: 통합몰별 대표카테고리 유무 및 전시여부 체크
        var gridRows = AUIGrid.getGridData(this.myGrid);
        var mallGroups = {};
        for (var i = 0; i < gridRows.length; i++) {
            var row = gridRows[i];
            // 삭제된 행은 검증에서 제외
            if (row._$isDeleted) continue;

            if (!mallGroups[row.dpmlNo]) {
                mallGroups[row.dpmlNo] = { name: row.dpmlNm, rows: [] };
            }
            mallGroups[row.dpmlNo].rows.push(row);
        }

        for (var dpmlNo in mallGroups) {
            var items = mallGroups[dpmlNo].rows;
            var repItem = null;
            for (var j = 0; j < items.length; j++) {
                if (items[j].repCtgYn === 'Y') {
                    repItem = items[j];
                    break;
                }
            }

            if (!repItem) {
                alert(mallGroups[dpmlNo].name + " 의 전체 대표카테고리가 없습니다.");
                return;
            }

            if (repItem.useYn !== 'Y') {
                alert("대표 카테고리는 사용여부가 Y 인 카테고리가 등록되어야 합니다.");
                return;
            }
        }

        var requestData = {
            create: addedItems,
            update: updatedItems,
            delete: removedItems
        };

        axios.post('/api/v1/display/standardDisplayCategoryConnect/saveStandardDisplayCategoryConnect.do', requestData)
            .then(function (res) {
                if (res.data && res.data.success) {
                    alert("성공적으로 저장되었습니다.");
                    StdDispCategoryConnect.loadConnectGrid(StdDispCategoryConnect.currentStdCtgNo);
                } else {
                    alert("저장 실패: " + (res.data.error ? res.data.error.message : "관리자에게 문의하세요"));
                }
            })
            .catch(function (err) {
                alert("저장 중 시스템 오류가 발생했습니다.");
            });
    },

    resetGrid: function () {
        AUIGrid.restore(this.myGrid);
    },

    bindEvents: function () {
        var _this = this;
        document.getElementById("btnAddRow").addEventListener("click", function () { _this.addRow(); });
        document.getElementById("btnDelRow").addEventListener("click", function () { _this.removeRow(); });
        document.getElementById("btnResetGrid").addEventListener("click", function () { _this.resetGrid(); });
        document.getElementById("btnSaveGrid").addEventListener("click", function () { _this.saveGridData(); });
    }
};

document.addEventListener("DOMContentLoaded", function () {
    StdDispCategoryConnect.init();
});

function popupDisplayCategoryCallback(data) {
    StdDispCategoryConnect.onPopupApply(data);
}
