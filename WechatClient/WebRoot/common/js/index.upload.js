$(function () {
    var imageflag = $("img[type=imgupload]");
    //<input type="file" capture="camera" upurl="<%=path + Constants.ROOT %>" callback="uploadback" style="display:none;"/>
    var inputupload = imageflag.next();
    var callback = "", upurl = "";
    if(!inputupload.is("input")){
    	callback = "uploadback";
    	upurl = contextPath + "/img/upload";
    	inputupload = $(document.createElement("input"));
    	inputupload.attr("type", "file");
    	inputupload.attr("capture", "camera");
    	inputupload.attr("upurl", upurl);
    	inputupload.attr("callback", callback);
    	$(document.body).append(inputupload);
    	imageflag.after(inputupload);
    }else{
    	callback = inputupload.attr("callback");
    	upurl = inputupload.attr("upurl");
    }
    inputupload.on("change", function () {
    	WxchatClient.Dialog.show("图片正在上传...");
        lrz(this.files[0], {width: 400}, function (results) {
        	console.info(results);
            // 你需要的数据都在这里，可以以字符串的形式传送base64给服务端转存为图片。
            var callbackfun = null;
            if(callback && callback != null && callback != ""){
            	eval("callbackfun = function(r){"+callback+"(r);};");
            }
            // 以下为演示用内容
            setTimeout(function () {
                // 发送到后端
                var xhr = new XMLHttpRequest();
                var data = {
                    base64: results.base64,
                    size: results.base64.length // 校验用，防止未完整接收
                };

                xhr.open('POST', upurl);
                xhr.setRequestHeader('Content-Type', 'application/json; charset=utf-8');
                xhr.onreadystatechange = function () {
                    if (xhr.readyState === 4 && xhr.status === 200) {
                        var result = JSON.parse(xhr.response);
                        if(result.error){
                        	WxchatClient.Dialog.close();
                        	WxchatClient.Dialog.show('服务端错误，未能保存图片');
                        }else if(callbackfun != null){
                        	callbackfun(result);
                        }
                    }
                };
                xhr.send(JSON.stringify(data)); // 发送base64
            }, 100);
        });
    });
    inputupload.hide();
    imageflag.on("click", function(){
    	inputupload.trigger("click");
    	return false;
    });

    /**
     * 演示报告
     * @param title
     * @param src
     * @param size
     */
    function demo_report(title, src, size) {
        var img = new Image(),
            li = document.createElement('li'),
            size = (size / 1024).toFixed(2) + 'KB';

        img.onload = function () {
            var content = '<ul>' +
                '<li>' + title + '（' + img.width + ' X ' + img.height + '）</li>' +
                '<li class="text-cyan">' + size + '</li>' +
                '</ul>';

            li.className = 'item';
            li.innerHTML = content;
            li.appendChild(img);
            document.querySelector('#report').appendChild(li);
        };

        img.src = src;
    }
});