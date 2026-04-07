/**
 * 표준분류-전시카테고리 연결 관리 자바스크립트 컨트롤러 (Restored Full Version)
 */
var StdDispCategoryConnect = {
    gridId: "connectGrid",
    myGrid: null,
    zTreeObj: null,
    currentStdCtgNo: null,
    leafCtgYn: 'N',

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
        // AS-IS RealGrid 컬럼을 AUIGrid 컬럼 구조로 정교하게 매핑
        var columnLayout = [
            { dataField: "siteNm", headerText: "사이트명", width: 120, editable: false, style: "disable-column", cellMerge: true },
            { dataField: "dpmlNm", headerText: "전시몰명", width: 120, editable: false, style: "disable-column", cellMerge: true },
            { dataField: "dispCtgNo", headerText: "전시카테고리 번호", width: 130, editable: false, style: "disable-column" },
            { dataField: "dispCtgNm", headerText: "전시카테고리 경로", width: 350, style: "left-text disable-column", editable: false },
            {
                dataField: "repCtgYn", headerText: "대표 여부 *", width: 120,
                renderer: {
                    type: "CheckBoxEditRenderer",
                    editable: true,
                    checkValue: "Y",
                    unCheckValue: "N",
                    displayWithCheckbox: true
                }
            },
            {
                dataField: "useYn", headerText: "사용 여부 *", width: 100,
                renderer: {
                    type: "CheckBoxEditRenderer",
                    editable: true,
                    checkValue: "Y",
                    unCheckValue: "N",
                    displayWithCheckbox: true
                }
            },
            { dataField: "sysModId", headerText: "수정자", width: 100, editable: false, style: "disable-column" },
            { dataField: "sysModDtm", headerText: "수정일시", width: 150, editable: false, style: "disable-column" }
        ];

        var gridProps = {
            editable: true,
            headerHeight: 40,
            rowHeight: 40,
            selectionMode: "multipleRows",
            showRowNumColumn: true,
            showRowCheckColumn: true, 
            enableCellMerge: true,
            noDataMessage: "연결할 대상 분류를 좌측 트리에서 먼저 선택해 주세요.",
            // 대표 카테고리 배타적 선택 로직 (이벤트 핸들러)
            headerTooltip: { show: true }
        };

        this.myGrid = AUIGrid.create("#" + this.gridId, columnLayout, gridProps);

        // AS-IS: 전시몰별 대표 카테고리 Y로 변경 시 다른 Row는 N으로 변경
        AUIGrid.bind(this.myGrid, "cellEditEnd", function(event) {
            if(event.dataField === "repCtgYn" && event.value === "Y") {
                var dpmlNo = event.item.dpmlNo;
                var allData = AUIGrid.getGridData(_this.myGrid);
                
                // 동일 몰의 다른 행들을 N으로 변경
                allData.forEach(function(row, idx) {
                    if(row.dpmlNo === dpmlNo && idx !== event.rowIndex && row.repCtgYn === "Y") {
                        AUIGrid.updateRowById(_this.myGrid, {
                            ...row,
                            repCtgYn: "N"
                        }, row.dispCtgNo); // dispCtgNo를 ID로 사용한다고 가정
                    }
                });
            }
        });
    },

    loadTree: function () {
        var _this = this;
        var treeSetting = {
            view: { 
                showLine: true, 
                selectedMulti: false,
                fontCss: function(treeId, treeNode) {
                    return treeNode.leafCtgYn === 'N' ? { 'color': '#999' } : { 'color': '#333', 'font-weight': 'bold' };
                }
            },
            data: {
                simpleData: { enable: true, idKey: "stdCtgNo", pIdKey: "uprStdCtgNo", rootPId: null },
                key: { name: "stdCtgNm" }
            },
            callback: {
                onClick: function (event, treeId, treeNode) {
                    _this.leafCtgYn = treeNode.leafCtgYn;
                    if (treeNode.leafCtgYn !== 'Y') {
                        // AS-IS: 리프가 아니면 그리드 비우기 및 안내
                        AUIGrid.clearGridData(_this.myGrid);
                        return false; 
                    }
                    
                    _this.currentStdCtgNo = treeNode.stdCtgNo;
                    _this.loadConnectGrid(treeNode.stdCtgNo);
                }
            }
        };

        axios.get("/api/v1/display/standardCategoryMgmt/getStandardCategoryMgmt.do")
            .then(function (res) {
                if (res.data && res.data.success) {
                    var treeData = res.data.data.map(function (item) {
                        return {
                            ...item,
                            stdCtgNo: String(item.stdCtgNo),
                            uprStdCtgNo: (item.uprStdCtgNo && item.uprStdCtgNo !== '0') ? String(item.uprStdCtgNo) : null,
                            isParent: (item.leafCtgYn === "N"),
                            open: false
                        };
                    });
                    _this.zTreeObj = $.fn.zTree.init($("#stdCategoryTree"), treeSetting, treeData);
                }
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
        });
    },

    onAdd: function () {
        var _this = this;
        if(this.leafCtgYn !== 'Y'){
            alert("하위 카테고리가 없는 최종(Leaf) 표준분류를 선택해 주세요.");
            return;
        }

        // AS-IS 팝업 호출 규격 재현 (Tree 버전)
        var pin = { argSelectType: "2", argUseYn: "", argSiteNo: "", argDpmlNo: "" };
        
        // s2bmig 환경의 Tree 팝업 URL
        var url = '/display/displayCategoryMgmtPopup/displayCategoryTreeListPopup.do';
        
        // 팝업 콜백 함수 (AUIGrid에 행 추가)
        window.popupDisplayCategoryCallback = function(data) {
            var resultData = JSON.parse(data);
            var gridData = AUIGrid.getGridData(_this.myGrid);
            var existingIds = gridData.map(function(item) { return item.dispCtgNo; });

            var uniqueData = resultData.filter(function(item) {
                return !existingIds.includes(item.dispCtgNo);
            }).map(function(item) {
                return {
                    stdCtgNo: _this.currentStdCtgNo,
                    siteNo: item.siteNo,
                    siteNm: item.siteNm,
                    dpmlNo: item.dpmlNo,
                    dpmlNm: item.dpmlNm,
                    dispCtgNo: item.dispCtgNo,
                    dispCtgNm: item.hierarchyNm, // 트리에서 제공하는 전체 경로명 사용
                    repCtgYn: "N",
                    useYn: "N"
                };
            });

            if(uniqueData.length < resultData.length) {
                alert("이미 등록된 전시카테고리는 제외하고 추가되었습니다.");
            }
            
            AUIGrid.addRow(_this.myGrid, uniqueData, "last");
        };

        // 팝업 오픈 (파라미터 전달 보완)
        var queryString = $.param(pin);
        var finalUrl = url + "?" + queryString;

        if (typeof popCommon === 'function') {
            popCommon(pin, { url: finalUrl, width: 1000, height: 700 }, window.popupDisplayCategoryCallback);
        } else {
            var popWin = window.open(finalUrl, "displayCategoryPopup", "width=1000,height=700,scrollbars=yes");
        }
    },

    onDelete: function () {
        var checkedItems = AUIGrid.getCheckedRowItems(this.myGrid);
        if (checkedItems.length === 0) {
            alert("삭제할 행을 선택해 주세요.");
            return;
        }

        // AS-IS: 대표 카테고리는 삭제 불가
        for(var i=0; i<checkedItems.length; i++) {
            if(checkedItems[i].item.repCtgYn === 'Y') {
                alert("대표 카테고리는 삭제할 수 없습니다. 다른 카테고리를 먼저 대표로 지정해 주세요.");
                return;
            }
        }
        AUIGrid.removeCheckedRows(this.myGrid);
    },

    onSave: function () {
        var _this = this;
        if (!this.currentStdCtgNo) return;

        var addedItems = AUIGrid.getAddedRowItems(this.myGrid);
        var updatedItems = AUIGrid.getEditedRowItems(this.myGrid);
        var removedItems = AUIGrid.getRemovedItems(this.myGrid);

        if (addedItems.length === 0 && updatedItems.length === 0 && removedItems.length === 0) {
            alert("저장할 변경 내용이 없습니다.");
            return;
        }

        // 대표 카테고리 검증 (전시몰별로 최소 1개는 Y여야 함)
        var allData = AUIGrid.getGridData(this.myGrid);
        var malls = {};
        allData.forEach(function(row) {
            if(!malls[row.dpmlNo]) malls[row.dpmlNo] = { nm: row.dpmlNm, hasRep: false };
            if(row.repCtgYn === 'Y') malls[row.dpmlNo].hasRep = true;
        });

        for(var mId in malls) {
            if(!malls[mId].hasRep) {
                alert("[" + malls[mId].nm + "] 몰에 REPRESENTATIVE(대표) 카테고리가 지정되지 않았습니다.");
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
                    _this.loadConnectGrid(_this.currentStdCtgNo);
                } else {
                    alert("저장 실패: " + (res.data.data || "오류가 발생했습니다."));
                }
            }).catch(function(err) {
                alert("서버 통신 중 오류가 발생했습니다.");
            });
    },

    bindEvents: function () {
        var _this = this;
        $("#btnAddRow").on("click", function () { _this.onAdd(); });
        $("#btnDelRow").on("click", function () { _this.onDelete(); });
        $("#btnSaveGrid").on("click", function () { _this.onSave(); });
    }
};

$(document).ready(function () {
    StdDispCategoryConnect.init();
});
