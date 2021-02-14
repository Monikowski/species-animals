import {
    clearElementChildren,
    createLinkCell,
    createButtonCell,
    createTextCell,
    getParameterByName
} from '../js/dom_utils.js';
import {getBackendUrl} from '../js/configuration.js';

window.addEventListener('load', () => {
    let addNewSpeciesSection = document.getElementById("addNewSpeciesSection")
    addNewSpeciesSection.prepend(createButtonCell('Add new species', () => location.href='../species_add/species_add.html'));
    fetchAndDisplayAllSpecies();
});

/**
 * Fetches all species and modifies the DOM tree in order to display them.
 */
function fetchAndDisplayAllSpecies() {
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            displayAllSpecies(JSON.parse(this.responseText))
        }
    };
    xhttp.open("GET", getBackendUrl() + '/api/species_all', true);
    xhttp.send();
}

/**
 * Updates the DOM tree in order to display all species.
 *
 * @param {{species_all: string[]}} species_all
 */
function displayAllSpecies(species_all) {
    let tableBody = document.getElementById('tableBody');
    clearElementChildren(tableBody);
    species_all.speciess.forEach(species => {
        tableBody.appendChild(createTableRow(species));
    })
}

/**
 * Creates single table row for entity.
 *
 * @param {string} species
 * @returns {HTMLTableRowElement}
 */
function createTableRow(species) {
    let tr = document.createElement('tr');
    tr.appendChild(createTextCell(species));
    tr.appendChild(createLinkCell('view', '../species_view/species_view.html?species=' + species));
    tr.appendChild(createLinkCell('edit', '../species_edit/species_edit.html?species=' + species));
    tr.appendChild(createButtonCell('delete', () => deleteSpecies(species)));
    return tr;
}

/**
 * Deletes entity from backend and reloads table.
 *
 * @param {string } species to be deleted
 */
function deleteSpecies(species) {
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 202) {
            fetchAndDisplayAllSpecies();
        }
    };
    xhttp.open("DELETE", getBackendUrl() + '/api/species_all/' + species, true);
    xhttp.send();
}
