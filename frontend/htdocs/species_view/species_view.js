import {
    getParameterByName,
    clearElementChildren,
    createLinkCell,
    createButtonCell,
    createTextCell,
    setTextNode
} from '../js/dom_utils.js';
import {getBackendUrl} from '../js/configuration.js';

window.addEventListener('load', () => {
    let addNewAnimalSection = document.getElementById("addNewAnimalSection")
    addNewAnimalSection.prepend(createButtonCell('Add new animal', () => location.href = '../animal_add/animal_add.html?species=' + getParameterByName('species')));
    fetchAndDisplaySpecies();
    fetchAndDisplayAnimals();
});

/**
 * Fetches all species and modifies the DOM tree in order to display them.
 */
function fetchAndDisplayAnimals() {
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            displayAnimals(JSON.parse(this.responseText))
        }
    };
    xhttp.open("GET", getBackendUrl() + '/api/species_all/' + getParameterByName('species') + '/animals', true);
    xhttp.send();
}

/**
 * Updates the DOM tree in order to display animals.
 *
 * @param {{animals: {id: number, name:string}[]}} animals
 */
function displayAnimals(animals) {
    let tableBody = document.getElementById('tableBody');
    clearElementChildren(tableBody);
    animals.animals.forEach(animal => {
        tableBody.appendChild(createTableRow(animal));
    })
}

/**
 * Creates single table row for entity.
 *
 * @param {{id: number, name: string}} animal
 * @returns {HTMLTableRowElement}
 */
function createTableRow(animal) {
    let tr = document.createElement('tr');
    tr.appendChild(createTextCell(animal.name));
    tr.appendChild(createLinkCell('view', '../animal_view/animal_view.html?species='
        + getParameterByName('species') + '&animal=' + animal.id));
    tr.appendChild(createLinkCell('edit', '../animal_edit/animal_edit.html?species='
        + getParameterByName('species') + '&animal=' + animal.id));
    tr.appendChild(createButtonCell('delete', () => deleteAnimal(animal.id)));
    return tr;
}

/**
 * Deletes entity from backend and reloads table.
 *
 * @param {number} animal to be deleted
 */
function deleteAnimal(animal) {
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 202) {
            fetchAndDisplayAnimals();
        }
    };
    xhttp.open("DELETE", getBackendUrl() + '/api/species_all/' + getParameterByName('species')
        + '/animals/' + animal, true);
    xhttp.send();
}


/**
 * Fetches single species and modifies the DOM tree in order to display it.
 */
function fetchAndDisplaySpecies() {
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            displaySpecies(JSON.parse(this.responseText))
        }
    };
    xhttp.open("GET", getBackendUrl() + '/api/species_all/' + getParameterByName('species'), true);
    xhttp.send();
}

/**
 * Updates the DOM tree in order to display species.
 *
 * @param {{species_name: string, food_type: string, origin_location:string, average_age:int}} species
 */
function displaySpecies(species) {
    setTextNode('species_name', species.species_name);
    setTextNode('food_type', species.food_type);
    setTextNode('origin_location', species.origin_location);
    setTextNode('average_age', String(species.average_age));
}
