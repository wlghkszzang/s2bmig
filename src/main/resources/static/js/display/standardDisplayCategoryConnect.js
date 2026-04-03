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
        // AS-IS RealGrid 컬럼을 AUIGrid 컬럼 구조로 매핑
        var columnLayout = [
            { dataField: "dispCtgNo", headerText: "전시카테고리 번호", width: 150, editable: false },
            { dataField: "dispCtgNm", headerText: "전시카테고리 명", width: 300, style: "left-text" },
            {
                dataField: "dispYn", headerText: "전시 여부", width: 100,
                editRenderer: {
                    type: "DropDownListRenderer",
                    list: [{ code: "Y", value: "전시" }, { code: "N", value: "미전시" }],
                    keyField: "code", valueField: "value"
                }
            }
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
    },

    loadTree: function () {
        var _this = this;
        var treeSetting = {
            view: { 
                showLine: true, 
                selectedMulti: false,
                fontCss: function(treeId, treeNode) {
                    // AS-IS: 리프가 아니면 음영 처리 (회색)
                    return treeNode.leafCtgYn === 'N' ? { 'color': '#999' } : { 'color': '#333' };
                }
            },
            data: {
                simpleData: { enable: true, idKey: "stdCtgNo", pIdKey: "uprStdCtgNo", rootPId: null },
                key: { name: "stdCtgNm" }
            },
            callback: {
                onClick: function (event, treeId, treeNode) {
                    // AS-IS 원칙: 리프 노드일 때만 이벤트 발생
                    if (treeNode.leafCtgYn !== 'Y') {
                        return false; 
                    }
                    
                    _this.currentStdCtgNo = treeNode.stdCtgNo;
                    _this.loadConnectGrid(treeNode.stdCtgNo);
                }
            }
        };
        // AS-IS: getStandardDisplayCategoryConnectTree.do -> 재사용!
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
        
        // AS-IS: getStandardDisplayCategoryConnect.do
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
            alert("좌측 트리에서 연결할 대상을 먼저 선택해주세요.");
            return;
        }
        var newItem = {
            dispCtgNo: "자동발번", // 나중에 백엔드가 처리
            dispCtgNm: "",
            dispYn: "Y",
            stdCtgNo: this.currentStdCtgNo
        };
        AUIGrid.addRow(this.myGrid, newItem, "last");
    },

    removeRow: function () {
        AUIGrid.removeCheckedRows(this.myGrid);
    },

    saveGridData: function () {
        if (!this.currentStdCtgNo) return;

        var addedItems = AUIGrid.getAddedRowItems(this.myGrid);
        var updatedItems = AUIGrid.getEditedRowItems(this.myGrid);
        var removedItems = AUIGrid.getRemovedItems(this.myGrid);

        if (addedItems.length === 0 && updatedItems.length === 0 && removedItems.length === 0) {
            alert("변경된 데이터가 없습니다.");
            return;
        }

        var requestData = {
            create: addedItems,
            update: updatedItems,
            delete: removedItems
        };

        // AS-IS: saveStandardDisplayCategoryConnect.do
        axios.post('/api/v1/display/standardDisplayCategoryConnect/saveStandardDisplayCategoryConnect.do', requestData)
            .then(function (res) {
                if (res.data && res.data.success) {
                    alert("그리드 데이터가 성공적으로 저장되었습니다.");
                    // 재조회
                    StdDispCategoryConnect.loadConnectGrid(StdDispCategoryConnect.currentStdCtgNo);
                } else {
                    alert("저장 실패: " + (res.data.error ? res.data.error.message : "관리자에게 문의하세요"));
                }
            })
            .catch(function (err) {
                alert("저장 중 시스템 오류가 발생했습니다.");
            });
    },

    bindEvents: function () {
        var _this = this;
        document.getElementById("btnAddRow").addEventListener("click", function () { _this.addRow(); });
        document.getElementById("btnDelRow").addEventListener("click", function () { _this.removeRow(); });
        document.getElementById("btnSaveGrid").addEventListener("click", function () { _this.saveGridData(); });
    }
};

document.addEventListener("DOMContentLoaded", function () {
    StdDispCategoryConnect.init();
});
