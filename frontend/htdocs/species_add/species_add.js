import {getParameterByName} from '../js/dom_utils.js';
import {getBackendUrl} from '../js/configuration.js';

window.addEventListener('load', () => {
    const infoForm = document.getElementById('infoForm');
    infoForm.addEventListener('submit', event => addInfoAction(event));
});

/**
 * Action event handled for adding species info.
 * @param {Event} event dom event
 */
function addInfoAction(event) {
    event.preventDefault();
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState === 4 && this.status === 201) {
            location.href = '../species_list/species_list.html';
        }
    }
    xhttp.open("POST", getBackendUrl() + '/api/species_all',true);
    const request = {
        'species_name': document.getElementById('species_name').value,
        'food_type': document.getElementById('food_type').value,
        'origin_location': document.getElementById('origin_location').value,
        'average_age': parseInt(document.getElementById('average_age').value),
    };

    xhttp.setRequestHeader('Content-Type', 'application/json');

    xhttp.send(JSON.stringify(request));
}



