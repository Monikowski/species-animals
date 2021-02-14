import {
    clearElementChildren,
    createLinkCell,
    createButtonCell,
    createTextCell,
    getParameterByName
} from '../js/dom_utils.js';
import {getBackendUrl} from '../js/configuration.js';

window.addEventListener('load', () => {
    let addNewFileSection = document.getElementById("addNewFileSection")
    addNewFileSection.prepend(createButtonCell('Add new file', () => location.href='../file_add/file_add.html'));
    fetchAndDisplayAllFiles();
});

function fetchAndDisplayAllFiles() {
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            displayAllFiles(JSON.parse(this.responseText))
        }
    };
    xhttp.open("GET", getBackendUrl() + '/api/files', true);
    xhttp.send();
}

/**
 * Updates the DOM tree in order to display all species.
 *
 * @param {{files_all: string[]}} files_all
 */
function displayAllFiles(files_all) {
    let tableBody = document.getElementById('tableBody');
    clearElementChildren(tableBody);
    files_all.detailss.forEach(details => {
        tableBody.appendChild(createTableRow(details));
    })
}

/**
 * Creates single table row for entity.
 *
 * @param {string} details
 * @returns {HTMLTableRowElement}
 */
function createTableRow(details) {
    let tr = document.createElement('tr');
    tr.appendChild(createTextCell(details.id));
    tr.appendChild(createTextCell(details.file_name));
    tr.appendChild(createTextCell(details.some_text));
    tr.appendChild(createTextCell(details.email));
    tr.appendChild(createLinkCell('download', getBackendUrl() + '/api/files/' + details.id));
    return tr;
}