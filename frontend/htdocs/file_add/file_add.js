import {getParameterByName} from '../js/dom_utils.js';
import {getBackendUrl} from '../js/configuration.js';

window.addEventListener('load', () => {
    const fileForm = document.getElementById('fileForm');
    fileForm.addEventListener('submit', event => addInfoAction(event));
});

/**
 * Action event handled for adding animal info.
 * @param {Event} event dom event
 */
/*
function addInfoAction(event) {
    var formElement = document.getElementById("file");
    var formData = new FormData(formElement);
    var request = new XMLHttpRequest();
    request.setRequestHeader('Content-Type', 'multipart/form-data');
    request.open("POST", getBackendUrl() + '/api/files');
    request.send(formData);
}
*/

function addInfoAction(event) {
    event.preventDefault();


    const request = new XMLHttpRequest();
    request.onreadystatechange = function() {
        if (this.readyState === 4 && this.status === 201) {
            location.href = '../file_list/file_list.html'
        }
    };
    request.open("POST", getBackendUrl() + '/api/files', true);

    const formData = new FormData();
    formData.append("file", document.getElementById("file").files[0])
    formData.append("info", document.getElementById("info").value)
    formData.append("email", document.getElementById("email").value)

    request.send(formData);
}

/*
function addInfoAction(event) {
    event.preventDefault();

    let file = document.getElementById("file").files[0];

    let formData = new FormData();
    formData.append("file", file);
    fetch(getBackendUrl() + '/api/files', {method: "POST", body: formData});
}*/


