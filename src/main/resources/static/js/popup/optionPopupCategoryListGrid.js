/**
 * 단품옵션조회 팝업 1단계 전용 AUI Grid 컨트롤러 (Vanilla JS)
 */
var OptionPopupCategoryGrid = {
    id: "optionPopupCategoryListGrid",
    grid: null,

    init: function() {
        var _this = this;

        // 컬럼 레이아웃 정의 (팝업은 조회용이므로 editable: false 강제 적용)
        var columnLayout = [
            { dataField: "optnCatNo", headerText: "옵션분류번호", width: 100, editable: false },
            {
                dataField: "optnCatRegGbCd", headerText: "사용처", width: 100, editable: false,
                labelFunction: function(rowIndex, columnIndex, value, headerText, item) {
                    return window._optnCatRegGbCd_select ? (window._optnCatRegGbCd_select[value] || value) : value;
                }
            },
            { dataField: "entrNm", headerText: "협력사명", width: 150, editable: false },
            {
                dataField: "optnCatTypCd", headerText: "분류유형", width: 100, editable: false,
                labelFunction: function(rowIndex, columnIndex, value, headerText, item) {
                    return window._optnCatTypCd_select ? (window._optnCatTypCd_select[value] || value) : value;
                }
            },
            { dataField: "optnCatNm", headerText: "옵션분류명", width: 150, style: "aui-text-left", editable: false },
            { dataField: "useYn", headerText: "사용여부", width: 80, editable: false,
                labelFunction: function(rowIndex, columnIndex, value, headerText, item) {
                    return value === 'Y' ? '사용' : '미사용';
                }
            }
        ];

        var gridProps = {
            editable: false,                   // 조회 전용
            showStateColumn: false,            // 상태 컬럼 숨김
            showRowCheckColumn: true,          // 라디오버튼/체크박스 표시
            rowCheckToRadio: true,             // 단일 선택만 가능하도록 라디오 버튼화
            selectionMode: "singleRow",        // 한 줄씩만 클릭 선택 가능
            noDataMessage: "검색 결과가 없습니다."
        };

        this.grid = AUIGrid.create(this.id, columnLayout, gridProps);
        this.bindEvents();
        
        // 초기 로딩 시 자동 검색 (파라미터가 있을 경우)
        document.getElementById("btn_search").click();
    },

    bindEvents: function() {
        var _this = this;

        // [검색] 버튼 리스너
        document.getElementById("btn_search").addEventListener("click", function(e) {
            e.preventDefault();
            var form = document.getElementById("optionPopupCategoryListGridForm");
            var formData = new FormData(form);
            var params = {};
            formData.forEach(function(value, key) {
                params[key] = value;
            });

            // 이미 존재하는 공통 카테고리 조회 API 재활용!
            axios.get('/api/v1/goods/optionMgmt/getOptionCategoryList.do', { params: params })
                .then(function(res) {
                    AUIGrid.setGridData(_this.grid, res.data);
                })
                .catch(function(err) {
                    console.error("옵션 분류 조회 실패", err);
                    alert("옵션 분류 리스트를 불러오는 중 오류가 발생했습니다.");
                });
        });

        // [초기화] 버튼 리스너
        document.getElementById("btn_init").addEventListener("click", function(e) {
            e.preventDefault();
            document.getElementById("optionPopupCategoryListGridForm").reset();
            AUIGrid.clearGridData(_this.grid);
        });

        // [더블클릭] 이벤트 리스너 -> 부모 창으로 데이터 넘김 처리
        AUIGrid.bind(_this.grid, "cellDoubleClick", function(e) {
            _this.applySelection(e.item);
        });

        // [확인(적용)] 하단 버튼 리스너
        document.getElementById("btn_apply_option").addEventListener("click", function(e) {
            e.preventDefault();
            var checkedItems = AUIGrid.getCheckedRowItems(_this.grid);
            if (checkedItems.length === 0) {
                alert("적용할 옵션분류를 그리드에서 선택해주세요.");
                return;
            }
            _this.applySelection(checkedItems[0].item);
        });

        // [취소(닫기)] 버튼 리스너
        document.getElementById("btn_close_popup").addEventListener("click", function(e) {
            e.preventDefault();
            window.close();
        });
    },

    // 부모 창으로 데이터 반환 로직 추상화
    applySelection: function(item) {
        // window.opener 객체가 존재하고, 콜백함수가 정의되어 있다면 호출합니다.
        // 콜백 명칭은 화면마다 다를 수 있으니 통용되는 `callbackOptionPopup` 혹은 부모에서 정의한 변수를 찾습니다.
        if (window.opener && !window.opener.closed) {
            if (typeof window.opener.callbackOptionPopup === 'function') {
                window.opener.callbackOptionPopup(item);
                window.close();
            } else {
                console.warn("부모 창에 callbackOptionPopup(item) 함수가 없습니다.", item);
                alert("콜백 함수가 연결되지 않았습니다. (테스트 환경일 수 있습니다)\n선택 항목: " + item.optnCatNm);
                window.close();
            }
        } else {
            console.error("부모 창을 찾을 수 없습니다.");
            alert("부모 창이 닫혀있어 데이터를 반환할 수 없습니다.");
            window.close();
        }
    }
};

document.addEventListener("DOMContentLoaded", function() {
    OptionPopupCategoryGrid.init();
});
