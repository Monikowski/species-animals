import {getParameterByName} from '../js/dom_utils.js';
import {getBackendUrl} from '../js/configuration.js';

window.addEventListener('load', () => {
    const infoForm = document.getElementById('infoForm');

    infoForm.addEventListener('submit', event => updateInfoAction(event));

    fetchAndDisplaySpecies();
});

/**
 * Fetches currently chosen species and updates edit form.
 */
function fetchAndDisplaySpecies() {
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            let response = JSON.parse(this.responseText);
            for (const [key, value] of Object.entries(response)) {
                let input = document.getElementById(key);
                if (input) {
                    input.value = value;
                }
            }
        }
    };
    xhttp.open("GET", getBackendUrl() + '/api/species_all/' + getParameterByName('species'));
    xhttp.send();
}

/**
 * Action event handled for updating animal info.
 * @param {Event} event dom event
 */
function updateInfoAction(event) {
    event.preventDefault();

    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 202) {
            fetchAndDisplaySpecies();
            location.href = '../species_view/species_view.html?species=' + getParameterByName('species')
        }
    };
    xhttp.open("PUT", getBackendUrl() + '/api/species_all/' + getParameterByName('species'), true);

    const request = {
        'species_name': document.getElementById('species_name').value,
        'food_type': document.getElementById('food_type').value,
        'origin_location': document.getElementById('origin_location').value,
        'average_age': parseInt(document.getElementById('average_age').value)
    };

    xhttp.setRequestHeader('Content-Type', 'application/json');

    xhttp.send(JSON.stringify(request));
}


