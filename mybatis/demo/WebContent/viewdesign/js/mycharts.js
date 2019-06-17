//options
var ec_opts = {
    pie: {
        title: {
            text: '饼状图',
            subtext: '',
            x: 'center'
        },
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            left: 'left',
            data: ['直接访问', '邮件营销', '联盟广告', '视频广告', '搜索引擎']
        },
        series: [{
            name: '访问来源',
            type: 'pie',
            radius: '55%',
            center: ['50%', '60%'],
            data: [
                { value: 335, name: '直接访问' },
                { value: 310, name: '邮件营销' },
                { value: 234, name: '联盟广告' },
                { value: 135, name: '视频广告' },
                { value: 1548, name: '搜索引擎' }
            ],
            itemStyle: {
                emphasis: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            }
        }]
    },
    bar: {
        title: {
            text: '柱状图'
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: { // 坐标轴指示器，坐标轴触发有效
                type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        legend: {
            data: ['直接访问']
        },        
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis: [{
            type: 'category',
            data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
            axisTick: {
                alignWithLabel: true
            }
        }],
        yAxis: [{
            type: 'value'
        }],
        series: [{
            name: '直接访问',
            type: 'bar',
            barWidth: '60%',
            data: [10, 52, 200, 334, 390, 330, 220]
        }],
        textStyle: {
            fontSize: 12
        }
    },
    bar_x: {
        title: {
            text: '条形图'
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'shadow'
            }
        },
        legend: {
            data: ['2011年', '2012年']
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis: {
            type: 'value',
            boundaryGap: [0, 0.01]
        },
        yAxis: {
            type: 'category',
            data: ['巴西', '印尼', '美国', '印度', '中国', '世界人口(万)']
        },
        series: [{
                name: '2011年',
                type: 'bar',
                data: [18203, 23489, 29034, 104970, 131744, 630230]
            },
            {
                name: '2012年',
                type: 'bar',
                data: [19325, 23438, 31000, 121594, 134141, 681807]
            }
        ]
    },
    line: {
        title: {
            text: '折线图'
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: ['机构一', '机构二', '机构三']
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis: {
            type: 'category',
            boundaryGap: false,
            data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日']
        },
        yAxis: {
            type: 'value'
        },
        series: [{
                name: '机构一',
                type: 'line',
                stack: '总量',
                data: [150, 232, 201, 154, 190, 330, 410]
            },
            {
                name: '机构二',
                type: 'line',
                stack: '总量',
                data: [320, 332, 301, 334, 390, 330, 320]
            },
            {
                name: '机构三',
                type: 'line',
                stack: '总量',
                data: [820, 932, 901, 934, 1290, 1330, 1320]
            }
        ]
    },
    scatter: {
        backgroundColor: new echarts.graphic.RadialGradient(0.3, 0.3, 0.8, [{
            offset: 0,
            color: '#f7f8fa'
        }, {
            offset: 1,
            color: '#cdd0d5'
        }]),
        title: {
            text: '散点图'
        },
        legend: {
            right: 10,
            data: ['1990', '2015']
        },
        xAxis: {
            splitLine: {
                lineStyle: {
                    type: 'dashed'
                }
            }
        },
        yAxis: {
            splitLine: {
                lineStyle: {
                    type: 'dashed'
                }
            },
            scale: true
        },
        dataZoom: {
            type: 'inside',
        },
        series: [{
            name: '1990',
            data: [
                [28604, 77, 17096869, 'Australia', 1990],
                [31163, 77.4, 27662440, 'Canada', 1990],
                [1516, 68, 1154605773, 'China', 1990],
                [13670, 74.7, 10582082, 'Cuba', 1990],
                [28599, 75, 4986705, 'Finland', 1990],
                [29476, 77.1, 56943299, 'France', 1990],
                [31476, 75.4, 78958237, 'Germany', 1990],
                [28666, 78.1, 254830, 'Iceland', 1990],
                [1777, 57.7, 870601776, 'India', 1990],
                [29550, 79.1, 122249285, 'Japan', 1990],
                [2076, 67.9, 20194354, 'North Korea', 1990],
                [12087, 72, 42972254, 'South Korea', 1990],
                [24021, 75.4, 3397534, 'New Zealand', 1990],
                [43296, 76.8, 4240375, 'Norway', 1990],
                [10088, 70.8, 38195258, 'Poland', 1990],
                [19349, 69.6, 147568552, 'Russia', 1990],
                [10670, 67.3, 53994605, 'Turkey', 1990],
                [26424, 75.7, 57110117, 'United Kingdom', 1990],
                [37062, 75.4, 252847810, 'United States', 1990]
            ],
            type: 'scatter',
            symbolSize: function(data) {
                return Math.sqrt(data[2]) / 5e2;
            },
            label: {
                emphasis: {
                    show: true,
                    formatter: function(param) {
                        return param.data[3];
                    },
                    position: 'top'
                }
            },
            itemStyle: {
                normal: {
                    shadowBlur: 10,
                    shadowColor: 'rgba(120, 36, 50, 0.5)',
                    shadowOffsetY: 5
                }
            }
        }, {
            name: '2015',
            data: [
                [44056, 81.8, 23968973, 'Australia', 2015],
                [43294, 81.7, 35939927, 'Canada', 2015],
                [13334, 76.9, 1376048943, 'China', 2015],
                [21291, 78.5, 11389562, 'Cuba', 2015],
                [38923, 80.8, 5503457, 'Finland', 2015],
                [37599, 81.9, 64395345, 'France', 2015],
                [44053, 81.1, 80688545, 'Germany', 2015],
                [42182, 82.8, 329425, 'Iceland', 2015],
                [5903, 66.8, 1311050527, 'India', 2015],
                [36162, 83.5, 126573481, 'Japan', 2015],
                [1390, 71.4, 25155317, 'North Korea', 2015],
                [34644, 80.7, 50293439, 'South Korea', 2015],
                [34186, 80.6, 4528526, 'New Zealand', 2015],
                [64304, 81.6, 5210967, 'Norway', 2015],
                [24787, 77.3, 38611794, 'Poland', 2015],
                [23038, 73.13, 143456918, 'Russia', 2015],
                [19360, 76.5, 78665830, 'Turkey', 2015],
                [38225, 81.4, 64715810, 'United Kingdom', 2015],
                [53354, 79.1, 321773631, 'United States', 2015]
            ],
            type: 'scatter',
            symbolSize: function(data) {
                return Math.sqrt(data[2]) / 5e2;
            },
            label: {
                emphasis: {
                    show: true,
                    formatter: function(param) {
                        return param.data[3];
                    },
                    position: 'top'
                }
            },
            itemStyle: {
                normal: {
                    shadowBlur: 10,
                    shadowColor: 'rgba(25, 100, 150, 0.5)',
                    shadowOffsetY: 5
                }
            }
        }]
    },
    radar: {
        title: {
            text: '雷达图'
        },
        tooltip: {},
        legend: {
            data: ['预算分配（Allocated Budget）', '实际开销（Actual Spending）']
        },
        radar: {
            // shape: 'circle',
            indicator: [
                { name: '销售（sales）', max: 6500 },
                { name: '管理（Administration）', max: 16000 },
                { name: '信息技术（Information Techology）', max: 30000 },
                { name: '客服（Customer Support）', max: 38000 },
                { name: '研发（Development）', max: 52000 },
                { name: '市场（Marketing）', max: 25000 }
            ]
        },
        series: [{
            name: '预算 vs 开销（Budget vs spending）',
            type: 'radar',
            data: [{
                    value: [4300, 10000, 28000, 35000, 50000, 19000],
                    name: '预算分配（Allocated Budget）'
                },
                {
                    value: [5000, 14000, 28000, 31000, 42000, 21000],
                    name: '实际开销（Actual Spending）'
                }
            ]
        }]
    },
    gauge: {
        title: {
            text: '仪表盘'
        },
        tooltip: {
            formatter: "{a} <br/>{b} : {c}%"
        },
        series: [{
            name: '业务指标',
            type: 'gauge',
            detail: { formatter: '{value}%' },
            data: [{ value: 50, name: '完成率' }]
        }]
    },
    funnel: {
        title: {
            text: '漏斗图',
            subtext: '纯属虚构'
        },
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c}%"
        },
        toolbox: {
            feature: {
                dataView: { readOnly: false },
                restore: {},
                saveAsImage: {}
            }
        },
        legend: {
            data: ['展现', '点击', '访问', '咨询', '订单']
        },
        calculable: true,
        series: [{
            name: '漏斗图',
            type: 'funnel',
            left: '10%',
            top: 60,
            //x2: 80,
            bottom: 60,
            width: '80%',
            // height: {totalHeight} - y - y2,
            min: 0,
            max: 100,
            minSize: '0%',
            maxSize: '100%',
            sort: 'descending',
            gap: 2,
            label: {
                normal: {
                    show: true,
                    position: 'inside'
                },
                emphasis: {
                    textStyle: {
                        fontSize: 20
                    }
                }
            },
            labelLine: {
                normal: {
                    length: 10,
                    lineStyle: {
                        width: 1,
                        type: 'solid'
                    }
                }
            },
            itemStyle: {
                normal: {
                    borderColor: '#fff',
                    borderWidth: 1
                }
            },
            data: [
                { value: 60, name: '访问' },
                { value: 40, name: '咨询' },
                { value: 20, name: '订单' },
                { value: 80, name: '点击' },
                { value: 100, name: '展现' }
            ]
        }]
    },
    heatmap: {
        title: {
            text: '热力图'
        },
        tooltip: {
            position: 'top'
        },
        animation: false,
        grid: {
            height: '50%',
            y: '10%'
        },
        dataZoom: {
            type: 'inside',
        },
        xAxis: {
            type: 'category',
            data: ['12a', '1a', '2a', '3a', '4a', '5a', '6a', '7a', '8a', '9a', '10a', '11a', '12p', '1p', '2p', '3p', '4p', '5p', '6p', '7p', '8p', '9p', '10p', '11p'],
            splitArea: {
                show: true
            }
        },
        yAxis: {
            type: 'category',
            data: ['Saturday', 'Friday', 'Thursday', 'Wednesday', 'Tuesday', 'Monday', 'Sunday'],
            splitArea: {
                show: true
            }
        },
        visualMap: {
            min: 0,
            max: 10,
            calculable: true,
            orient: 'horizontal',
            left: 'center',
            bottom: '15%'
        },
        series: [{
            name: 'Punch Card',
            type: 'heatmap',
            data: [
                [0, 0, 5],
                [0, 1, 1],
                [0, 2, 0],
                [0, 3, 0],
                [0, 4, 0],
                [0, 5, 0],
                [0, 6, 0],
                [0, 7, 0],
                [0, 8, 0],
                [0, 9, 0],
                [0, 10, 0],
                [0, 11, 2],
                [0, 12, 4],
                [0, 13, 1],
                [0, 14, 1],
                [0, 15, 3],
                [0, 16, 4],
                [0, 17, 6],
                [0, 18, 4],
                [0, 19, 4],
                [0, 20, 3],
                [0, 21, 3],
                [0, 22, 2],
                [0, 23, 5],
                [1, 0, 7],
                [1, 1, 0],
                [1, 2, 0],
                [1, 3, 0],
                [1, 4, 0],
                [1, 5, 0],
                [1, 6, 0],
                [1, 7, 0],
                [1, 8, 0],
                [1, 9, 0],
                [1, 10, 5],
                [1, 11, 2],
                [1, 12, 2],
                [1, 13, 6],
                [1, 14, 9],
                [1, 15, 11],
                [1, 16, 6],
                [1, 17, 7],
                [1, 18, 8],
                [1, 19, 12],
                [1, 20, 5],
                [1, 21, 5],
                [1, 22, 7],
                [1, 23, 2],
                [2, 0, 1],
                [2, 1, 1],
                [2, 2, 0],
                [2, 3, 0],
                [2, 4, 0],
                [2, 5, 0],
                [2, 6, 0],
                [2, 7, 0],
                [2, 8, 0],
                [2, 9, 0],
                [2, 10, 3],
                [2, 11, 2],
                [2, 12, 1],
                [2, 13, 9],
                [2, 14, 8],
                [2, 15, 10],
                [2, 16, 6],
                [2, 17, 5],
                [2, 18, 5],
                [2, 19, 5],
                [2, 20, 7],
                [2, 21, 4],
                [2, 22, 2],
                [2, 23, 4],
                [3, 0, 7],
                [3, 1, 3],
                [3, 2, 0],
                [3, 3, 0],
                [3, 4, 0],
                [3, 5, 0],
                [3, 6, 0],
                [3, 7, 0],
                [3, 8, 1],
                [3, 9, 0],
                [3, 10, 5],
                [3, 11, 4],
                [3, 12, 7],
                [3, 13, 14],
                [3, 14, 13],
                [3, 15, 12],
                [3, 16, 9],
                [3, 17, 5],
                [3, 18, 5],
                [3, 19, 10],
                [3, 20, 6],
                [3, 21, 4],
                [3, 22, 4],
                [3, 23, 1],
                [4, 0, 1],
                [4, 1, 3],
                [4, 2, 0],
                [4, 3, 0],
                [4, 4, 0],
                [4, 5, 1],
                [4, 6, 0],
                [4, 7, 0],
                [4, 8, 0],
                [4, 9, 2],
                [4, 10, 4],
                [4, 11, 4],
                [4, 12, 2],
                [4, 13, 4],
                [4, 14, 4],
                [4, 15, 14],
                [4, 16, 12],
                [4, 17, 1],
                [4, 18, 8],
                [4, 19, 5],
                [4, 20, 3],
                [4, 21, 7],
                [4, 22, 3],
                [4, 23, 0],
                [5, 0, 2],
                [5, 1, 1],
                [5, 2, 0],
                [5, 3, 3],
                [5, 4, 0],
                [5, 5, 0],
                [5, 6, 0],
                [5, 7, 0],
                [5, 8, 2],
                [5, 9, 0],
                [5, 10, 4],
                [5, 11, 1],
                [5, 12, 5],
                [5, 13, 10],
                [5, 14, 5],
                [5, 15, 7],
                [5, 16, 11],
                [5, 17, 6],
                [5, 18, 0],
                [5, 19, 5],
                [5, 20, 3],
                [5, 21, 4],
                [5, 22, 2],
                [5, 23, 0],
                [6, 0, 1],
                [6, 1, 0],
                [6, 2, 0],
                [6, 3, 0],
                [6, 4, 0],
                [6, 5, 0],
                [6, 6, 0],
                [6, 7, 0],
                [6, 8, 0],
                [6, 9, 0],
                [6, 10, 1],
                [6, 11, 0],
                [6, 12, 2],
                [6, 13, 1],
                [6, 14, 3],
                [6, 15, 4],
                [6, 16, 0],
                [6, 17, 0],
                [6, 18, 0],
                [6, 19, 0],
                [6, 20, 1],
                [6, 21, 2],
                [6, 22, 2],
                [6, 23, 6]
            ].map(function(item) {
                return [item[1], item[0], item[2] || '-'];
            }),
            label: {
                normal: {
                    show: true
                }
            },
            itemStyle: {
                emphasis: {
                    shadowBlur: 10,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            }
        }]
    }
};
//初始化图表
function ecinit(id, option) {
    echartInit(document.getElementById(id), option);
}
function echartInit(dom, option) {
    var myChart = echarts.init(dom,"macarons");
    myChart.setOption(option);
    window.onresize = myChart.resize;
}

//input绑定change事件
$('.c-edit').bind('change', function() {
    var v = $(this).val(),
        t = $(this).prev().html(),
        n = $(this).attr('name').split('|'),
        id = $('.charts.fd-view-current').attr('id'),
        dom = echarts.getInstanceByDom(document.getElementById(id)),
        domOpt = dom.getOption();
    setValue(dom, domOpt, n, t, v);
});
function loadValue(view) {
    var id = $(view).attr('id'),
        dom = echarts.getInstanceByDom(document.getElementById(id)),
        domOpt = dom.getOption(),
        inputs = $('.c-edit');
    var i, n, val,len = inputs.length;
    for (i = 0; i < len; i++) {
        n = $(inputs[i]).attr('name').split('|');
        if (n[0] == 'color') {
           val = domOpt[n[0]][n[1]];
           $(inputs[i]).css('background-color', val);
        }else if (!domOpt[n[0]]) {
            val = '';
        }else if (!n[2] && !n[3]) {
            if (domOpt[n[0]].length < 1 || !Array.isArray(domOpt[n[0]])) {
                val = domOpt[n[0]][n[1]];
            } else {
                val = domOpt[n[0]][0][n[1]];
            }
        } else {
            if (!n[3]) {
                val = domOpt[n[0]][0][n[1]][n[2]];
            } else {
                val = domOpt[n[0]][0][n[1]][n[2]][n[3]];
            }
        }
        $(inputs[i]).val(val);
    }
};
function setValue(dom, domOpt, n, t, v) {
    if (!domOpt[n[0]]) {
        toastr.warning("该图例无此属性");
        return;
    }
    if (n[0] == 'color') {
        domOpt[n[0]][n[1]] = v;
        dom.setOption(domOpt);
    } else {
        if (!n[2] && !n[3]) {
            if (domOpt[n[0]].length < 1 || !Array.isArray(domOpt[n[0]])) {
                domOpt[n[0]][n[1]] = v;
            } else {
                domOpt[n[0]][0][n[1]] = v;
            }
        } else {
            if (!n[3]) {
                domOpt[n[0]][0][n[1]][n[2]] = v;
            } else {
                domOpt[n[0]][0][n[1]][n[2]][n[3]] = v;
            }
        }
        dom.setOption(domOpt);
        toastr.success("设置" + t + "为" + v);
    }
}