/**
 * 전시 카테고리 관리 자바스크립트 컨트롤러 (AUIGrid + zTree + Axios)
 */
var DisplayCategoryMgmt = {
    subGridId: "subCategoryGrid",
    goodsGridId: "displayGoodsGrid",
    subGrid: null,
    goodsGrid: null,
    zTreeObj: null,

    init: function () {
        this.createGrids();
        this.bindEvents();
        this.loadTree();
    },

    createGrids: function () {
        // 1. Sub Category Grid
        var subColumnLayout = [
            { dataField: "dispCtgNo", headerText: "카테고리 번호", width: 120, editable: false },
            { dataField: "dispCtgNm", headerText: "카테고리명", width: 250, style: "left-text" },
            { dataField: "dispSeq", headerText: "전시순서", width: 80, dataType: "numeric" },
            {
                dataField: "useYn", headerText: "사용여부", width: 80,
                editRenderer: {
                    type: "DropDownListRenderer",
                    list: [{ code: "Y", value: "사용" }, { code: "N", value: "미사용" }],
                    keyField: "code", valueField: "value"
                }
            },
            {
                dataField: "leafYn", headerText: "리프여부", width: 80,
                editRenderer: {
                    type: "DropDownListRenderer",
                    list: [{ code: "Y", value: "예" }, { code: "N", value: "아니오" }],
                    keyField: "code", valueField: "value"
                }
            },
            { dataField: "sysModId", headerText: "수정자", width: 100, editable: false },
            { dataField: "sysModDtm", headerText: "수정일시", width: 150, editable: false }
        ];

        // 2. Display Goods Grid
        var goodsColumnLayout = [
            { dataField: "goodsNo", headerText: "상품번호", width: 120, editable: false },
            { dataField: "goodsNm", headerText: "상품명", width: 300, style: "left-text", editable: false },
            { dataField: "saleStatCd", headerText: "판매상태", width: 100, editable: false },
            { dataField: "norPrc", headerText: "정상가", width: 100, dataType: "numeric", style: "right-text", editable: false },
            { dataField: "salePrc", headerText: "판매가", width: 100, dataType: "numeric", style: "right-text", editable: false },
            {
                dataField: "dispYn", headerText: "전시여부", width: 80,
                editRenderer: {
                    type: "DropDownListRenderer",
                    list: [{ code: "Y", value: "전시" }, { code: "N", value: "미전시" }],
                    keyField: "code", valueField: "value"
                }
            },
            { dataField: "sysModId", headerText: "수정자", width: 100, editable: false },
            { dataField: "sysModDtm", headerText: "수정일시", width: 150, editable: false }
        ];

        var gridProps = {
            editable: true,
            headerHeight: 40,
            rowHeight: 40,
            selectionMode: "multipleRows",
            showRowNumColumn: true,
            showRowCheckColumn: true,
            noDataMessage: "조회된 데이터가 없습니다."
        };

        this.subGrid = AUIGrid.create("#" + this.subGridId, subColumnLayout, gridProps);
        this.goodsGrid = AUIGrid.create("#" + this.goodsGridId, goodsColumnLayout, gridProps);
    },

    loadTree: function () {
        var _this = this;
        var siteNo = document.getElementById("tree_siteNo").value;
        var useYn = document.getElementById("tree_useYn").value;
        
        var $loader = $("#treeLoader");
        $loader.show();

        var treeSetting = {
            view: { showLine: true, selectedMulti: false },
            data: {
                simpleData: {
                    enable: true,
                    idKey: "dispCtgNo",
                    pIdKey: "uprDispCtgNo",
                    rootPId: null
                },
                key: { name: "dispCtgNm" }
            },
            callback: {
                onClick: function (event, treeId, treeNode) {
                    _this.loadCategoryDetail(treeNode.dispCtgNo);
                }
            }
        };

        axios.get("/api/v1/display/displayCategoryMgmt/getCategoryTreeList.do", {
            params: { siteNo: siteNo, useYn: useYn, dpmlNo: '1' }
        })
        .then(function (res) {
            $loader.hide();
            if (res.data && res.data.success) {
                _this.zTreeObj = $.fn.zTree.init($("#categoryTree"), treeSetting, res.data.data);
                _this.zTreeObj.expandAll(false);
            }
        })
        .catch(function (err) {
            $loader.hide();
            console.error("Tree Load Error:", err);
        });
    },

    loadCategoryDetail: function (dispCtgNo) {
        var _this = this;
        axios.get("/api/v1/display/displayCategoryMgmt/getCategoryDetail.do", {
            params: { dispCtgNo: dispCtgNo }
        })
        .then(function (res) {
            if (res.data && res.data.success) {
                var data = res.data.data;
                // Map to Form
                document.getElementById("categoryInfo_siteNo").value = data.siteNo || "";
                document.getElementById("categoryInfo_siteNm").innerText = data.siteNm || "-";
                document.getElementById("categoryInfo_categoryNo").innerText = data.dispCtgNo || "-";
                document.getElementById("categoryInfo_dispCtgNo").value = data.dispCtgNo || "";
                document.getElementById("categoryInfo_categoryNm").value = data.dispCtgNm || "";
                document.getElementById("categoryInfo_dispSeq").value = data.dispSeq || 0;
                document.getElementById("categoryInfo_yes24CtgCd").value = data.yes24CtgCd || "";
                document.getElementById("categoryInfo_sysModId").innerText = data.sysModId || "";
                document.getElementById("categoryInfo_sysModDtm").innerText = data.sysModDtm || "";

                // Radios
                var useYnRadios = document.getElementsByName("useYn");
                useYnRadios.forEach(r => { if (r.value === data.useYn) r.checked = true; });
                var leafYnRadios = document.getElementsByName("leafYn");
                leafYnRadios.forEach(r => { 
                    if (r.value === data.leafYn) r.checked = true;
                    r.disabled = (data.leafCtgYnChangePossible === 'N');
                });

                // Handle Visibility
                if (data.leafYn === 'Y') {
                    document.getElementById("subCategoryGridSection").style.display = "none";
                    document.getElementById("displayGoodsGridSection").style.display = "block";
                    _this.loadGoodsGrid(dispCtgNo);
                    setTimeout(() => AUIGrid.resize(_this.goodsGrid), 50);
                } else {
                    document.getElementById("subCategoryGridSection").style.display = "block";
                    document.getElementById("displayGoodsGridSection").style.display = "none";
                    _this.loadSubGrid(dispCtgNo);
                    setTimeout(() => AUIGrid.resize(_this.subGrid), 50);
                }
            }
        });
    },

    loadSubGrid: function (dispCtgNo) {
        var _this = this;
        AUIGrid.showAjaxLoader(this.subGrid);
        axios.get("/api/v1/display/displayCategoryMgmt/getSubCategoryList.do", { params: { dispCtgNo: dispCtgNo } })
            .then(function (res) {
                AUIGrid.removeAjaxLoader(_this.subGrid);
                if (res.data && res.data.success) {
                    AUIGrid.setGridData(_this.subGrid, res.data.data);
                }
            });
    },

    loadGoodsGrid: function (dispCtgNo) {
        var _this = this;
        AUIGrid.showAjaxLoader(this.goodsGrid);
        axios.get("/api/v1/display/displayCategoryMgmt/getDisplayGoodsList.do", { 
            params: { dispCtgNo: dispCtgNo, rowsPerPage: 100, pageIdx: 1 } 
        })
        .then(function (res) {
            AUIGrid.removeAjaxLoader(_this.goodsGrid);
            if (res.data && res.data.success) {
                AUIGrid.setGridData(_this.goodsGrid, res.data.data); // RspDto wraps data
            }
        });
    },

    saveCategoryInfo: function () {
        var _this = this;
        var formData = {
            dispCtgNo: document.getElementById("categoryInfo_dispCtgNo").value,
            dispCtgNm: document.getElementById("categoryInfo_categoryNm").value,
            dispSeq: document.getElementById("categoryInfo_dispSeq").value,
            useYn: document.querySelector("input[name='useYn']:checked").value,
            leafYn: document.querySelector("input[name='leafYn']:checked").value,
            yes24CtgCd: document.getElementById("categoryInfo_yes24CtgCd").value
        };

        axios.post("/api/v1/display/displayCategoryMgmt/modifyDisplayCategory.do", formData)
            .then(function (res) {
                if (res.data && res.data.success) {
                    alert("저장되었습니다.");
                    _this.loadTree();
                } else {
                    alert("저장 실패: " + (res.data.error ? res.data.error.message : "알 수 없는 오류"));
                }
            });
    },

    saveSubGrid: function () {
        var _this = this;
        var added = AUIGrid.getAddedRowItems(this.subGrid);
        var edited = AUIGrid.getEditedRowItems(this.subGrid);
        var removed = AUIGrid.getRemovedItems(this.subGrid);

        if (added.length + edited.length + removed.length === 0) {
            alert("변경사항이 없습니다.");
            return;
        }

        var payload = {
            create: added,
            update: edited,
            delete: removed
        };

        axios.post("/api/v1/display/displayCategoryMgmt/saveCategoryList.do", payload)
            .then(function (res) {
                if (res.data && res.data.success) {
                    alert("목록이 저장되었습니다.");
                    var node = _this.zTreeObj.getSelectedNodes()[0];
                    if (node) _this.loadSubGrid(node.dispCtgNo);
                    _this.loadTree();
                }
            });
    },

    saveGoodsGrid: function () {
        var _this = this;
        var added = AUIGrid.getAddedRowItems(this.goodsGrid);
        var edited = AUIGrid.getEditedRowItems(this.goodsGrid);
        var removed = AUIGrid.getRemovedItems(this.goodsGrid);

        var payload = {
            create: added,
            update: edited,
            delete: removed
        };

        axios.post("/api/v1/display/displayCategoryMgmt/saveGoodsList.do", payload)
            .then(function (res) {
                if (res.data && res.data.success) {
                    alert("상품 목록이 저장되었습니다.");
                    var node = _this.zTreeObj.getSelectedNodes()[0];
                    if (node) _this.loadGoodsGrid(node.dispCtgNo);
                }
            });
    },

    bindEvents: function () {
        var _this = this;
        
        // Tree Options
        document.getElementById("tree_siteNo").addEventListener("change", () => this.loadTree());
        document.getElementById("tree_useYn").addEventListener("change", () => this.loadTree());

        // Buttons
        document.getElementById("btn_categoryInfo_save").addEventListener("click", () => this.saveCategoryInfo());
        
        document.getElementById("btn_subCategoryGrid_add").addEventListener("click", () => {
            var node = this.zTreeObj.getSelectedNodes()[0];
            if (!node) { alert("상위 카테고리를 선택해주세요."); return; }
            AUIGrid.addRow(this.subGrid, {
                uprDispCtgNo: node.dispCtgNo,
                siteNo: node.siteNo,
                dpmlNo: node.dpmlNo,
                dispCtgNm: "신규 카테고리",
                dispSeq: 1,
                useYn: "Y",
                leafYn: "Y"
            }, "last");
        });
        
        document.getElementById("btn_subCategoryGrid_remove").addEventListener("click", () => AUIGrid.removeCheckedRows(this.subGrid));
        document.getElementById("btn_subCategoryGrid_save").addEventListener("click", () => this.saveSubGrid());

        document.getElementById("btn_displayGoodsGrid_add").addEventListener("click", () => {
            var node = this.zTreeObj.getSelectedNodes()[0];
            if (!node) return;
            // Simple prompt for illustration, in real app this would be a popup
            var goodsNo = prompt("추가할 상품번호를 입력하세요.");
            if (goodsNo) {
                AUIGrid.addRow(this.goodsGrid, {
                    dispCtgNo: node.dispCtgNo,
                    goodsNo: goodsNo,
                    dispYn: "Y"
                }, "last");
            }
        });
        
        document.getElementById("btn_displayGoodsGrid_remove").addEventListener("click", () => AUIGrid.removeCheckedRows(this.goodsGrid));
        document.getElementById("btn_displayGoodsGrid_save").addEventListener("click", () => this.saveGoodsGrid());
    }
};

document.addEventListener("DOMContentLoaded", function () {
    DisplayCategoryMgmt.init();
});
