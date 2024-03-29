// import { html, render } from './lit-html.js';


const templateNode = document.createElement('template');


class MfloBirthdayDay extends HTMLElement {
    constructor() { 
        super();
        /* attachShadow works only on certain tags - see https://developer.mozilla.org/en-US/docs/Web/API/Element/attachShadow */
        this.shadow = this.attachShadow({mode: 'open'});
    }

    connectedCallback() { 
        if (this.isConnected){
            this.loadTemplateToShadow(this.shadow, 'MfloBirthdayDay.html');
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

customElements.define('mflo-birthday-day', MfloBirthdayDay);





