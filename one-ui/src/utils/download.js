import axios from 'axios'
import {Loading,Message} from "element-ui";


export function exportFile(url, params, fileName) {
  const loading = Loading.service({fullscreen: true, background: 'rgb(255,255,255,0.8)', text: '正在导出请稍候...'});
  axios({
    method: 'get',
    url: url,
    params: params,
    responseType: 'blob'
  }).then(response => {
    download(response.data, fileName);
    loading.close();
  }).catch((error) => {
    Message.error("导出失败")
  })
}

function download(data, fileName) {
  if (!data) {
    return
  }
  let url = window.URL.createObjectURL(new Blob([data]));
  let link = document.createElement('a');
  link.style.display = 'none';
  link.href = url;
  link.setAttribute('download', fileName);

  document.body.appendChild(link)
  link.click();
  document.body.removeChild(link);
  window.URL.revokeObjectURL(url);
}
