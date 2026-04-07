$.namespace("dcGrid.eventhandler");

(function (w, $, d, ns) {
    const app = {
        // 초기화
        init() {
            this.argCheck();
            this.gridLoading();
            this.bindButtonEvent();
        },
        argCheck(){
            if (_gridType != '1' && _gridType != 'N') {
                alert(_msg.invalidAccessMsg);
                w.close();
            }
            if(argSiteNo) this.changSiteNo();
        },

        gridLoading(){
            ns.grid.setEditOptions({ editable: false }) // 그리드 수정불가
            ns.grid.onItemChecked = function(grid, itemIndex, checked) {
                grid.setCurrent({ itemIndex: itemIndex })
            }
            ns.grid.setRowStyleCallback(function(grid, item, fixed) {
                if (item.checked) {
                    return 'rg-trcol_active'
                }
            })
        },

        bindButtonEvent(){
            const $container = $('.button-group');
            const eventId = '.buttonEvent';

            $container.on('click'+ eventId, '#btn_popup_list', () => this.onSearch(0,true));
            $container.on('click'+ eventId, '#btn_popup_init', () => $('#dcGridForm')[0].reset());
            $container.on('click'+ eventId, '#btn_popup_apply', () => this.onApply());
            $container.on('click'+ eventId, '#btn_popup_close', () => w.close());
            $('.input-group').on('change' + eventId, '#siteNo', () => this.changSiteNo());
            $("#dcGridForm").keypress(function (e){
                if (e.which == 13){
                    e.preventDefault();
                    $('#btn_popup_list').click();
                }
            });
        },
        changSiteNo() {
            const siteNo = $('#siteNo').val();

            const selectElement = d.getElementById('dpmlNo');

            // 선택된 사이트에 대한 몰 정보 리스트 추출
            const mallOnSiteList = mallList.filter(mall => mall.siteNo === siteNo);

            // 기존 option 삭제
            const fullOption = selectElement.firstElementChild;
            selectElement.replaceChildren(fullOption);

            // option 삽입
            if (mallOnSiteList.length === 0) return;


            mallOnSiteList.forEach(mall => {
                const option = d.createElement('option');
                option.value = mall.dpmlNo;
                option.text = mall.dpmlNm;
                if(mall.dpmlNo === argDpmlNo) option.selected = true;

                selectElement.appendChild(option);
            });
        },

        // 적용
        onApply() {
            if ( _gridType === '1' ) {
                const rowIndex = ns.grid.getCurrent().dataRow;
                if ( rowIndex === -1 ) { // 선택된 행이 없는 경우
                    alert(_msg.noSelectedDataMsg);
                    return;
                }
                this.returnData( rowIndex );

            } else {
                const rowIndexList = ns.grid.getCheckedRows();
                if ( !rowIndexList.length ) {
                    alert(_msg.noSelectedDataMsg);
                    return;
                }
                this.returnData(rowIndexList);
            }
        },
        returnData( data ) {
            const grid = ns.grid;
            const provider = grid.getDataSource();

            let returnData = [];
            const putReturnData = function ( dataRow ) {
                const row = provider.getJsonRow( dataRow );
                returnData.push(row);
            }

            if ( $.isArray(data) ) {
                data.forEach(i => putReturnData(i));
            } else {
                putReturnData(data);
            }

            window.postMessage(JSON.stringify(returnData), _baseUrl);
            window.close();
        }
        ,onSearch(pageNo,isOpenToast){
            ns.grid.cancel();

            pageNo = !pageNo ? 0 : pageNo;
            const pageFunc = function (pageNo) { return this.onSearch(pageNo); };
            ns.controller.doQuery(ns, '', pageNo, pageFunc, false, isOpenToast);
        },
        gridEvent : {
            onCellDblClicked : function(grid, clickData) {
                if ( clickData.cellType === 'gridEmpty' ) return;
                dcGrid.eventhandler.returnData( clickData.dataRow );
            }
        },
    }

    $.extend(ns, {
        gridEvent: app.gridEvent
    });

    $(function () {
        app.init();
    })

})(window, jQuery, document, dcGrid.eventhandler);