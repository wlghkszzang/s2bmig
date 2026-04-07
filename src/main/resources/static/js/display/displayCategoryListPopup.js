/**
 * 전시 카테고리 조회 팝업 (AUIGrid & Vanilla JS)
 */
var DisplayCategoryPopup = {
    gridId: "dcGrid",
    myGrid: null,

    init: function () {
        this.createGrid();
        this.bindEvents();
        // 자동 조회 기능 (필요시)
        this.onSearch();
    },

    createGrid: function () {
        var columnLayout = [
            { dataField: "siteNm", headerText: "사이트명", width: 120, cellMerge: true },
            { dataField: "dpmlNm", headerText: "전시몰명", width: 120, cellMerge: true },
            { dataField: "dispCtgNo", headerText: "번호", width: 100 },
            { dataField: "lrgCtgNm", headerText: "대분류", width: 120 },
            { dataField: "midCtgNm", headerText: "중분류", width: 120 },
            { dataField: "smlCtgNm", headerText: "소분류", width: 120 },
            { dataField: "thnCtgNm", headerText: "세분류", width: 120 },
            {
                dataField: "useYn", headerText: "사용여부", width: 80,
                renderer: {
                    type: "CheckBoxEditRenderer",
                    checkValue: "Y",
                    unCheckValue: "N",
                    displayWithCheckbox: true,
                    editable: false
                }
            }
        ];

        var gridProps = {
            headerHeight: 40,
            rowHeight: 40,
            showRowNumColumn: true,
            showRowCheckColumn: true, // 멀티 선택 가정
            enableCellMerge: true,
            selectionMode: "multipleRows"
        };

        this.myGrid = AUIGrid.create("#" + this.gridId, columnLayout, gridProps);
    },

    onSearch: function () {
        var _this = this;
        var params = {
            siteNo: document.getElementById("siteNo").value,
            dpmlNo: document.getElementById("dpmlNo").value,
            useYn: document.getElementById("useYn").value,
            dispCtgNm: document.getElementById("dispCtgNm").value
        };

        AUIGrid.showAjaxLoader(this.myGrid);
        axios.get("/popup/displayCategoryMgmtPopup.getDisplayCategoryList.do", { params: params })
            .then(function (res) {
                AUIGrid.removeAjaxLoader(_this.myGrid);
                if (res.data && res.data.success) {
                    AUIGrid.setGridData(_this.myGrid, res.data.data || []);
                }
            })
            .catch(function (err) {
                AUIGrid.removeAjaxLoader(_this.myGrid);
                console.error("Search error:", err);
            });
    },

    onApply: function () {
        var selectedItems = AUIGrid.getCheckedRowItems(this.myGrid);
        if (selectedItems.length === 0) {
            alert("선택된 항목이 없습니다.");
            return;
        }

        var data = selectedItems.map(function (item) { return item.item; });

        // 부모 창의 콜백 함수 호출 (JSON 문자열로 변환하여 전달 - 레거시 호환)
        if (window.opener && window.opener.popupDisplayCategoryCallback) {
            window.opener.popupDisplayCategoryCallback(JSON.stringify(data));
            window.close();
        } else {
            alert("부모 창의 콜백 함수를 찾을 수 없습니다.");
        }
    },

    onClose: function () {
        window.close();
    },

    bindEvents: function () {
        var _this = this;
        // 조회 버튼
        $("#btn_popup_list").on("click", function (e) {
            e.preventDefault();
            _this.onSearch();
        });

        // 초기화 버튼
        $("#btn_popup_init").on("click", function (e) {
            e.preventDefault();
            document.getElementById("dcGridForm").reset();
        });

        // 적용 버튼
        $("#btn_popup_apply").on("click", function (e) {
            e.preventDefault();
            _this.onApply();
        });

        // 닫기 버튼
        $("#btn_popup_close").on("click", function (e) {
            e.preventDefault();
            _this.onClose();
        });

        // 엔터키 조회
        $("#dispCtgNm").on("keypress", function (e) {
            if (e.which === 13) {
                _this.onSearch();
            }
        });
    }
};

$(document).ready(function () {
    DisplayCategoryPopup.init();
});
