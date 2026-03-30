/**
 * 속성 정보 조회 팝업 (AUIGrid 버전)
 */
var attCdInfoGrid;

var attCdInfoGridEventHandler = {
    id: "attCdInfoPopupGrid",

    init: function () {
        this.gridInit();
        this.bindEvents();
        this.search();
    },

    gridInit: function () {
        var columnLayout = [
            {
                dataField: "attCd",
                headerText: "속성코드",
                width: 100
            },
            {
                dataField: "attNm",
                headerText: "속성명",
                id: "attNm", // autoFillColumnId 매칭을 위해 id 설정
                style: "aui-left"
            },
            {
                dataField: "attValTypNm",
                headerText: "속성값유형",
                width: 140
            },
            {
                dataField: "attCdExpYn",
                headerText: "노출여부",
                width: 100
            }
        ];

        var gridProps = {
            width: "100%",
            height: 400,
            autoFillColumnId: "attNm", // '속성명' 컬럼이 남는 공간을 모두 채움
            showRowCheckColumn: true,
            showRowNumColumn: true,
            selectionMode: "singleRow",
            noDataMessage: "데이터가 없습니다."
        };

        attCdInfoGrid = AUIGrid.create(this.id, columnLayout, gridProps);

        // 창 크기 변경 시 대응
        window.onresize = function () {
            if (attCdInfoGrid) {
                AUIGrid.resize(attCdInfoGrid);
            }
        };

        // 더블 클릭 시 선택 적용
        AUIGrid.bind(attCdInfoGrid, "cellDoubleClick", function (event) {
            attCdInfoGridEventHandler.onApply();
        });
    },

    bindEvents: function () {
        var _this = this;

        // 적용 버튼
        document.getElementById("btn_apply").addEventListener("click", function (e) {
            e.preventDefault();
            _this.onApply();
        });

        // 닫기 버튼
        document.getElementById("btn_close").addEventListener("click", function (e) {
            e.preventDefault();
            window.close();
        });
    },

    search: function () {
        AUIGrid.showAjaxLoader(attCdInfoGrid);
        axios.get("/api/v1/display/standardCategoryMgmt/getAttCdInfo.do")
            .then(function (res) {
                AUIGrid.removeAjaxLoader(attCdInfoGrid);
                if (res.data && res.data.data) {
                    AUIGrid.setGridData(attCdInfoGrid, res.data.data);
                }
            })
            .catch(function (err) {
                AUIGrid.removeAjaxLoader(attCdInfoGrid);
                console.error("Search Error:", err);
            });
    },

    onApply: function () {
        var selectedItems = AUIGrid.getCheckedRowItems(attCdInfoGrid);

        // 체크된 것이 없으면 현재 선택된 행 사용
        if (selectedItems.length === 0) {
            var selectedRows = AUIGrid.getSelectedItems(attCdInfoGrid);
            if (selectedRows.length > 0) {
                selectedItems = [selectedRows[0]];
            }
        }

        if (selectedItems.length === 0) {
            alert("선택된 데이터가 없습니다.");
            return;
        }

        // AUIGrid getCheckedRowItems는 {rowIndex: x, columnIndex: y, item: {...}} 형태이므로 item만 추출
        var dataList = selectedItems.map(function (row) {
            return row.item || row; // getSelectedItems는 item이 직접 올 수 있음
        });

        if (window.opener && window.opener.onApplyAndClose) {
            window.opener.onApplyAndClose(dataList);
            window.close();
        }
    }
};

document.addEventListener("DOMContentLoaded", function () {
    attCdInfoGridEventHandler.init();
});
