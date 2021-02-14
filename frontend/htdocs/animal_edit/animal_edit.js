import {getParameterByName} from '../js/dom_utils.js';
import {getBackendUrl} from '../js/configuration.js';

window.addEventListener('load', () => {
    const infoForm = document.getElementById('infoForm');

    infoForm.addEventListener('submit', event => updateInfoAction(event));

    fetchAndDisplayAnimal();
});

/**
 * Fetches currently chosen species' animals and updates edit form.
 */
function fetchAndDisplayAnimal() {
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
    xhttp.open("GET", getBackendUrl() + '/api/species_all/' + getParameterByName('species') + '/animals/'
        + getParameterByName('animal'), true);
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
            fetchAndDisplayAnimal();
            location.href = '../animal_view/animal_view.html?species=' + getParameterByName('species') + '&animal=' + getParameterByName('animal');
        }
    };
    xhttp.open("PUT", getBackendUrl() + '/api/species_all/' + getParameterByName('species') + '/animals/'
        + getParameterByName('animal'), true);

    const request = {
        'name': document.getElementById('name').value,
        'age': parseInt(document.getElementById('age').value),
        'body_mass': parseInt(document.getElementById('body_mass').value),
        'daily_food_mass': parseInt(document.getElementById('daily_food_mass').value)
    };

    xhttp.setRequestHeader('Content-Type', 'application/json');

    xhttp.send(JSON.stringify(request));
}


