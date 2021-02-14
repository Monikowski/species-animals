import {
    getParameterByName,
    setTextNode
} from '../js/dom_utils.js';
import {getBackendUrl} from '../js/configuration.js';

window.addEventListener('load', () => {
    fetchAndDisplayAnimal();
});


/**
 * Fetches single animal and modifies the DOM tree in order to display it.
 */
function fetchAndDisplayAnimal() {
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            displaySpecies(JSON.parse(this.responseText))
        }
    };
    xhttp.open("GET", getBackendUrl() + '/api/species_all/' + getParameterByName('species') +
        '/animals/' + getParameterByName('animal'), true);
    xhttp.send();
}

/**
 * Updates the DOM tree in order to display the animal.
 *
 * @param {{name: string, age: int, body_mass:int, daily_food_mass:int}} animal
 */
function displaySpecies(animal) {
    setTextNode('animal_name', animal.name);
    setTextNode('species_name', getParameterByName('species'));
    setTextNode('age', String(animal.age));
    setTextNode('body_mass', String(animal.body_mass));
    setTextNode('daily_food_mass', String(animal.daily_food_mass));
}
