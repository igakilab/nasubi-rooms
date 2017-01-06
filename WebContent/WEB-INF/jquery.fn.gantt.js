<script>
        $(function() {
            "use strict";
            $(".gantt").gantt({
                source: [{
                    name: "設計",
                    desc: "外部",
                    values: [{
                        from: "/Date(1320192000000)/",
                        to: "/Date(1320392000000)/",
                        label: "外部",
                        customClass: "ganttRed"
                    }]
                },{
                    name: " ",
                    desc: "内部",
                    values: [{
                        from: "/Date(1320392000000)/",
                        to: "/Date(1320592000000)/",
                        label: "内部",
                        customClass: "ganttRed"
                    }]
                }],
                navigate: "scroll",
                maxScale: "hours",
                itemsPerPage: 10,
                months : ["1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"],
                dow : ["日", "月", "火", "水", "木", "金", "土"],
                onItemClick: function(data) {
                    alert("進捗バーがクリックされました。");
                },
                onAddClick: function(dt, rowId) {
                    alert("空白部分がクリックされました。");
                },
                onRender: function() {
                    if (window.console && typeof console.log === "function") {
                        console.log("chart rendered");
                    }
                }
            });
            prettyPrint();
        });
</script>