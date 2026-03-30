/**
 * 표준 카테고리 관리 자바스크립트 컨트롤러 (Vanilla JS + Axios)
 */
var StandardCategoryMgmt = {
    gridId: "childGrid",
    attrGridId: "attrGrid",
    goodsGridId: "goodsGrid",
    myGrid: null,
    attrGrid: null,
    goodsGrid: null,
    zTreeObj: null,

    init: function () {
        // jQuery 3.x compatibility patch for older zTree versions
        if (window.$ && !$.browser) {
            $.browser = { msie: false, version: 0 };
        }

        this.createGrids();
        this.loadTree();
        this.bindEvents();
    },

    createGrids: function () {
        // 1. Child Categories Grid
        var childColumnLayout = [
            { dataField: "stdCtgNo", headerText: "표준 분류 번호", width: 120, editable: false },
            { dataField: "stdCtgNm", headerText: "표준 분류명", width: 250, style: "left-text" },
            {
                dataField: "useYn", headerText: "사용 여부", width: 80,
                editRenderer: {
                    type: "DropDownListRenderer",
                    list: [{ code: "Y", value: "사용" }, { code: "N", value: "미사용" }],
                    keyField: "code", valueField: "value"
                }
            },
            { dataField: "sortSeq", headerText: "정렬 순서", width: 80, dataType: "numeric" },
            {
                dataField: "leafCtgYn", headerText: "리프 여부", width: 80,
                editRenderer: {
                    type: "DropDownListRenderer",
                    list: [{ code: "Y", value: "예" }, { code: "N", value: "아니오" }],
                    keyField: "code", valueField: "value"
                }
            },
            { dataField: "sysModId", headerText: "수정자", width: 100, editable: false },
            { dataField: "sysModDtm", headerText: "수정일시", width: 150, editable: false }
        ];

        // 2. Product Attributes Grid
        var attrColumnLayout = [
            { dataField: "attCd", headerText: "속성코드", width: 120, editable: false },
            { dataField: "attNm", headerText: "속성명", width: 200, style: "left-text", editable: false },
            { dataField: "attValTypNm", headerText: "속성값유형", width: 150, editable: false },
            { dataField: "dispSeq", headerText: "정렬순서", width: 80, dataType: "numeric" },
            {
                dataField: "useYn", headerText: "사용여부", width: 80,
                editRenderer: {
                    type: "DropDownListRenderer",
                    list: [{ code: "Y", value: "사용" }, { code: "N", value: "미사용" }],
                    keyField: "code", valueField: "value"
                }
            }
        ];

        // 3. Product List Grid
        var goodsColumnLayout = [
            { dataField: "goodsNo", headerText: "상품번호", width: 120, editable: false },
            { dataField: "goodsNm", headerText: "상품명", width: 350, style: "left-text", editable: false },
            { dataField: "saleStatCdNm", headerText: "판매상태", width: 120, editable: false }
        ];

        var gridProps = {
            editable: true,
            headerHeight: 40,
            rowHeight: 40,
            selectionMode: "multipleRows",
            showRowNumColumn: true,
            showRowCheckColumn: true, // 체크박스 컬럼 활성화
            noDataMessage: "데이터가 없습니다."
        };

        this.myGrid = AUIGrid.create("#" + this.gridId, childColumnLayout, gridProps);
        this.attrGrid = AUIGrid.create("#" + this.attrGridId, attrColumnLayout, gridProps);
        this.goodsGrid = AUIGrid.create("#" + this.goodsGridId, goodsColumnLayout, { ...gridProps, editable: false });
    },

    loadTree: function () {
        var _this = this;
        var $loader = $("#treeLoader");
        $loader.show();

        var treeSetting = {
            view: {
                showLine: true,
                showIcon: true,
                selectedMulti: false,
                dblClickExpand: false // Disable double click expand to handle it in onClick
            },
            data: {
                simpleData: {
                    enable: true,
                    idKey: "stdCtgNo",
                    pIdKey: "uprStdCtgNo",
                    rootPId: null // Changed to null to match normalized data
                },
                key: {
                    name: "stdCtgNm"
                }
            },
            callback: {
                onClick: function (event, treeId, treeNode) {
                    _this.loadCategoryDetail(treeNode.stdCtgNo);

                    if (treeNode.leafCtgYn === 'Y') {
                        // Show leaf grids, hide child category grid
                        document.getElementById("childCategoryGridSection").style.display = "none";
                        document.getElementById("leafNodeGridSection").style.display = "flex";
                        _this.loadAttrGrid(treeNode.stdCtgNo);
                        _this.loadGoodsGrid(treeNode.stdCtgNo);

                        // Force AUIGrid to recalculate layout after making container visible
                        setTimeout(function () {
                            AUIGrid.resize(_this.attrGrid);
                            AUIGrid.resize(_this.goodsGrid);
                        }, 50);
                    } else {
                        // Show child category grid, hide leaf grids
                        document.getElementById("childCategoryGridSection").style.display = "block";
                        document.getElementById("leafNodeGridSection").style.display = "none";
                        _this.loadChildGrid(treeNode.stdCtgNo);

                        // Force AUIGrid to recalculate layout after making container visible
                        setTimeout(function () {
                            AUIGrid.resize(_this.myGrid);
                        }, 50);
                    }

                    // Toggle expansion on single click if it's a parent
                    if (treeNode.isParent) {
                        _this.zTreeObj.expandNode(treeNode, !treeNode.open, false, true);
                    }
                }
            }
        };

        axios.get("/api/v1/display/standardCategoryMgmt/getStandardCategoryMgmt.do")
            .then(function (res) {
                $loader.hide();
                console.log("Tree Data Loaded:", res.data);
                if (res.data && res.data.success && res.data.data.length > 0) {
                    var treeData = res.data.data.map(function (item) {
                        return {
                            ...item,
                            stdCtgNo: String(item.stdCtgNo),
                            uprStdCtgNo: (item.uprStdCtgNo && item.uprStdCtgNo !== '0') ? String(item.uprStdCtgNo) : null,
                            isParent: (item.leafCtgYn === "N"),
                            open: false
                        };
                    });

                    var $treeTarget = $("#categoryTree");
                    if ($treeTarget.length > 0) {
                        _this.zTreeObj = $.fn.zTree.init($treeTarget, treeSetting, treeData);
                        console.log("zTree Initialized");
                    }
                } else {
                    console.warn("No tree data returned from server.");
                }
            })
            .catch(function (err) {
                $loader.hide();
                console.error("Tree loading error:", err);
            });
    },

    loadCategoryDetail: function (stdCtgNo) {
        var _this = this;
        axios.get("/api/v1/display/standardCategoryMgmt/getStandardCategoryMgmtInfo.do", { params: { stdCtgNo: stdCtgNo } })
            .then(function (res) {
                if (res.data && res.data.success) {
                    var data = res.data.data;
                document.getElementById("stdCtgNo").value = data.stdCtgNo || "";
                document.getElementById("stdCtgNm").value = data.stdCtgNm || "";
                document.getElementById("uprStdCtgNo").value = data.uprStdCtgNo || "";
                document.getElementById("uprStdCtgNm").value = data.uprStdCtgNm || "최상위";
                document.getElementById("sortSeq").value = data.sortSeq || 0;
                document.getElementById("mrgnRate").value = data.mrgnRate || 0;
                document.getElementById("hsCd").value = data.hsCd || "";
                document.getElementById("goodsNotiLisartCd").value = data.goodsNotiLisartCd || "";
                document.getElementById("goodsNotiLisartCdNm").value = data.goodsNotiLisartCdNm || "";
                document.getElementById("mdId").value = data.mdId || "";
                document.getElementById("mdNm").value = data.mdNm || "";

                // Radio buttons
                var useYnRadios = document.getElementsByName("useYn");
                useYnRadios.forEach(function (r) { if (r.value === data.useYn) r.checked = true; });

                var leafCtgYnRadios = document.getElementsByName("leafCtgYn");
                leafCtgYnRadios.forEach(function (r) {
                    if (r.value === data.leafCtgYn) r.checked = true;
                    r.disabled = (data.leafCtgYnChangePossible === 'N');
                });

                var safeCertiRadios = document.getElementsByName("safeCertiNeedYn");
                safeCertiRadios.forEach(function (r) { if (r.value === data.safeCertiNeedYn) r.checked = true; });

                // Footer Info
                document.getElementById("txtSysModId").innerText = data.sysModId || "-";
                document.getElementById("txtSysModDtm").innerText = data.sysModDtm ? data.sysModDtm.replace('T', ' ') : "-";
                }
            })
            .catch(function (err) {
                console.error("Detail loading error:", err);
            });
    },

    loadChildGrid: function (stdCtgNo) {
        var _this = this;
        AUIGrid.showAjaxLoader(this.myGrid);
        axios.get("/api/v1/display/standardCategoryMgmt/getStandardCategoryMgmtChildList.do", { params: { stdCtgNo: stdCtgNo } })
            .then(function (res) {
                AUIGrid.removeAjaxLoader(_this.myGrid);
                if (res.data && res.data.success) {
                    AUIGrid.setGridData(_this.myGrid, res.data.data);
                }
            })
            .catch(function (err) {
                AUIGrid.removeAjaxLoader(_this.myGrid);
                console.error("Grid loading error:", err);
            });
    },

    loadAttrGrid: function (stdCtgNo) {
        var _this = this;
        AUIGrid.showAjaxLoader(this.attrGrid);
        axios.get("/api/v1/display/standardCategoryMgmt/getStandardCategoryGoodsAttrList.do", { params: { stdCtgNo: stdCtgNo } })
            .then(function (res) {
                AUIGrid.removeAjaxLoader(_this.attrGrid);
                if (res.data && res.data.success) {
                    AUIGrid.setGridData(_this.attrGrid, res.data.data || []);
                }
            })
            .catch(function (err) {
                AUIGrid.removeAjaxLoader(_this.attrGrid);
                console.warn("Attr Grid loading failed:", err);
            });
    },

    loadGoodsGrid: function (stdCtgNo) {
        var _this = this;
        AUIGrid.showAjaxLoader(this.goodsGrid);
        axios.get("/api/v1/display/standardCategoryMgmt/getStandardCategoryMgmtGoodsList.do", { params: { stdCtgNo: stdCtgNo } })
            .then(function (res) {
                AUIGrid.removeAjaxLoader(_this.goodsGrid);
                if (res.data && res.data.success) {
                    AUIGrid.setGridData(_this.goodsGrid, res.data.data || []);
                }
            })
            .catch(function (err) {
                AUIGrid.removeAjaxLoader(_this.goodsGrid);
                console.error("Goods Grid loading error:", err);
            });
    },

    openAttCdInfoPopup: function () {
        var url = "/popup/standardCategory.attCdInfoPopup.do";
        var winWidth = 800;
        var winHeight = 600;
        var winLeft = (screen.width - winWidth) / 2;
        var winTop = (screen.height - winHeight) / 2;
        window.open(url, "attCdInfoPopup", "width=" + winWidth + ",height=" + winHeight + ",left=" + winLeft + ",top=" + winTop + ",scrollbars=yes");
    },

    onApplyAndClose: function (data) {
        var _this = this;
        var stdCtgNo = document.getElementById("stdCtgNo").value;

        data.forEach(function (item) {
            // 중복 체크
            var rows = AUIGrid.getGridData(_this.attrGrid);
            var isDuplicate = rows.some(function (row) {
                return row.attCd === item.attCd;
            });

            if (!isDuplicate) {
                var newItem = {
                    stdCtgNo: stdCtgNo,
                    attCd: item.attCd,
                    attNm: item.attNm,
                    attValTypCd: item.attValTypCd,
                    attValTypNm: item.attValTypNm,
                    dispSeq: rows.length + 1,
                    useYn: "Y",
                    attCdExpYn: item.attCdExpYn
                };
                AUIGrid.addRow(_this.attrGrid, newItem, "last");
            }
        });
    },

    saveCategoryInfo: function () {
        var stdCtgNo = document.getElementById("stdCtgNo").value;
        if (!stdCtgNo) {
            alert("선택된 카테고리가 없습니다.");
            return;
        }

        var formData = {
            stdCtgNo: stdCtgNo,
            stdCtgNm: document.getElementById("stdCtgNm").value,
            useYn: document.querySelector("input[name='useYn']:checked").value,
            leafCtgYn: document.querySelector("input[name='leafCtgYn']:checked").value,
            sortSeq: document.getElementById("sortSeq").value,
            mrgnRate: document.getElementById("mrgnRate").value,
            safeCertiNeedYn: document.querySelector("input[name='safeCertiNeedYn']:checked").value,
            hsCd: document.getElementById("hsCd").value,
            goodsNotiLisartCd: document.getElementById("goodsNotiLisartCd").value,
            mdId: document.getElementById("mdId").value
        };

        var _this = this;
        axios.post("/api/v1/display/standardCategoryMgmt/saveStandardCategoryMgmtInfo.do", formData)
            .then(function (res) {
                if (res.data && res.data.success) {
                    alert("성공적으로 저장되었습니다.");
                    _this.loadTree();
                }
            })
            .catch(function (err) {
                alert("저장 중 오류가 발생했습니다.");
            });
    },

    addRow: function () {
        var currentStdCtgNo = document.getElementById("stdCtgNo").value;
        if (!currentStdCtgNo) {
            alert("상위 카테고리를 먼저 선택해주세요.");
            return;
        }
        var newItem = {
            stdCtgNo: "",
            stdCtgNm: "신규 하위 분류",
            useYn: "Y",
            sortSeq: 1,
            leafCtgYn: "N",
            uprStdCtgNo: currentStdCtgNo
        };
        AUIGrid.addRow(this.myGrid, newItem, "last");
    },

    removeRow: function () {
        AUIGrid.removeCheckedRows(this.myGrid);
    },

    saveGridData: function () {
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

        axios.post('/api/v1/display/standardCategoryMgmt/saveStandardCategoryMgmtChildList.do', requestData)
            .then(function (res) {
                if (res.data && res.data.success) {
                    alert("그리드 데이터가 성공적으로 저장되었습니다.");
                }
            })
            .catch(function (err) {
                alert("저장 중 오류가 발생했습니다.");
            });
    },

    saveAttrData: function () {
        var addedItems = AUIGrid.getAddedRowItems(this.attrGrid);
        var updatedItems = AUIGrid.getEditedRowItems(this.attrGrid);
        var removedItems = AUIGrid.getRemovedItems(this.attrGrid);

        if (addedItems.length === 0 && updatedItems.length === 0 && removedItems.length === 0) {
            alert("변경된 데이터가 없습니다.");
            return;
        }

        var requestData = {
            create: addedItems,
            update: updatedItems,
            delete: removedItems
        };

        axios.post('/api/v1/display/standardCategoryMgmt/saveStandardCategoryGoodsAttrList.do', requestData)
            .then(function (res) {
                if (res.data && res.data.success) {
                    alert("속성 데이터가 성공적으로 저장되었습니다.");
                }
            })
            .catch(function (err) {
                alert("저장 중 오류가 발생했습니다.");
            });
    },

    exportToExcel: function (grid) {
        AUIGrid.exportToXlsx(grid, {
            fileName: "표준카테고리_데이터"
        });
    },

    bindEvents: function () {
        var _this = this;
        document.getElementById("btnSaveInfo").addEventListener("click", function () { _this.saveCategoryInfo(); });
        document.getElementById("btnAddRow").addEventListener("click", function () { _this.addRow(); });
        document.getElementById("btnDelRow").addEventListener("click", function () { _this.removeRow(); });
        document.getElementById("btnSaveGrid").addEventListener("click", function () { _this.saveGridData(); });

        // Excel Buttons
        document.getElementById("btnExcelChild").addEventListener("click", function () { _this.exportToExcel(_this.myGrid); });
        document.getElementById("btnExcelAttr").addEventListener("click", function () { _this.exportToExcel(_this.attrGrid); });
        document.getElementById("btnExcelGoods").addEventListener("click", function () { _this.exportToExcel(_this.goodsGrid); });

        // Reset Buttons
        document.getElementById("btnResetGrid").addEventListener("click", function () {
            if (confirm("저장되지 않은 변경사항을 초기화하시겠습니까?")) {
                var node = _this.zTreeObj.getSelectedNodes()[0];
                if (node) _this.loadChildGrid(node.stdCtgNo);
            }
        });
        document.getElementById("btnResetAttr").addEventListener("click", function () {
            if (confirm("저장되지 않은 변경사항을 초기화하시겠습니까?")) {
                var node = _this.zTreeObj.getSelectedNodes()[0];
                if (node) _this.loadAttrGrid(node.stdCtgNo);
            }
        });

        // Popup Buttons
        document.getElementById("btnCallNotiPopup").addEventListener("click", function () {
            alert("상품고시품목 팝업 기능은 시스템 레이아웃 구성 후 연동 예정입니다.");
        });

        // Leaf grid buttons
        var btnAddAttr = document.getElementById("btnAddAttr");
        if (btnAddAttr) btnAddAttr.addEventListener("click", function () { _this.openAttCdInfoPopup(); });
        var btnDelAttr = document.getElementById("btnDelAttr");
        if (btnDelAttr) btnDelAttr.addEventListener("click", function () { AUIGrid.removeCheckedRows(_this.attrGrid); });
        var btnSaveAttr = document.getElementById("btnSaveAttr");
        if (btnSaveAttr) btnSaveAttr.addEventListener("click", function () { _this.saveAttrData(); });
    }
};

document.addEventListener("DOMContentLoaded", function () {
    StandardCategoryMgmt.init();
});

// Callback from popup
function onApplyAndClose(data) {
    StandardCategoryMgmt.onApplyAndClose(data);
}
