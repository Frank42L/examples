// import { html, render } from './lit-html.js';


class StzhMkBirthdayBoard extends HTMLElement { 
    constructor() { 
        super();
        this.shadow = this.attachShadow({mode: 'open'});
    }

    connectedCallback() { 
        if (this.isConnected){
            this.loadTemplateToShadow(this.shadow, 'StzhMkBirthdayBoard.html');
        }
    }

    loadTemplateToShadow(targetElem, fileName){
        fetch(fileName)
            .then(r => r.text())
            .then(template => {
                targetElem.innerHTML = template;
            }
        );
    }

}

customElements.define('stzh-mk-birthday-board', StzhMkBirthdayBoard);

/*
        if (this.hasAttribute('title1')) {
            this.widgetTitle = this.getAttribute('title');
        } else {
            this.widgetTitle = '<NO TITLE>';
        }
*/

/*     static duplicateChildNodes(fromElem, toElem) {
        fromElem.querySelectorAll(':not([slot])').forEach ( (elem) => {
            console.log(elem);
            toElem.appendChild(elem.cloneNode(true));
        });
    }
 */   


