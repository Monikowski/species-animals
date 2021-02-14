import {getParameterByName} from '../js/dom_utils.js';
import {getBackendUrl} from '../js/configuration.js';

window.addEventListener('load', () => {
    const infoForm = document.getElementById('infoForm');
    infoForm.addEventListener('submit', event => addInfoAction(event));
});

/**
 * Action event handled for adding animal info.
 * @param {Event} event dom event
 */
function addInfoAction(event) {
    event.preventDefault();
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState === 4 && this.status === 201) {
            location.href = '../species_view/species_view.html?species=' + getParameterByName('species')
        }
    }
    xhttp.open("POST", getBackendUrl() + '/api/species_all/' + getParameterByName('species') + '/animals/', true);
    const request = {
        'name': document.getElementById('name').value,
        'age': parseInt(document.getElementById('age').value),
        'body_mass': parseInt(document.getElementById('body_mass').value),
        'daily_food_mass': parseInt(document.getElementById('daily_food_mass').value)
    };

    xhttp.setRequestHeader('Content-Type', 'application/json');

    xhttp.send(JSON.stringify(request));
}



