$(document).ready(function(){
    //orderSummSet();
});

function isArray(obj){
    if(Array.isArray){
        return Array.isArray(obj);
    }else{
        return Object.prototype.toString.call(obj)==="[object Array]";
    }
}

function dataSet(defaultData, data){
    for(var da in data){
        if(typeof data[da] === "object" && !isArray(data[da])){
            dataSet(defaultData[da], data[da]);
        }else{
            defaultData[da] = data[da];
        }
    }
}

function orderSummSet(data) {
    var para = {
        id: "echarts_pie_flow",
        tooltip: {
            show: true,
            formatter: "{b} : {c}人"
        },
        legend: {
            orient: 'vertical',
            x: 10,
            y: 10,
            itemGap: 10,
            data: ['预约人数', '取号人数', '取消人数', '超时人数']
        },
        seriesArray: {
            name: "",
            radius: [[80, 100], [55, 75], [30, 50], [5, 25]],
            startAngle:[0, 10, 40, 100],
            data: [
                {name: ['预约人数'], value: [500]},
                {name: ['取号人数', 'invisible'], value: [400, 100]},
                {name: ['取消人数', 'invisible'], value: [40, 440]},
                {name: ['超时人数', 'invisible'], value: [60, 460]}
            ],
            itemStyle: {
                emphasis: {},
                normal: {
                    label: {
                        align:"center",
                        position: "outer",
                        formatter: "{b}\n{c}人"
                    },
                    labelLine: {
                        length:60
                    }
                }
            }
        }
    };
    eChartsPie(para);
}