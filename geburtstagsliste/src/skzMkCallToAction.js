// import { html, render } from './lit-html.js';


const templateNode = document.createElement('template');

/*
    StzhMKCallToAction 
    - Attribute 'destination' = Url to be called 
    - Attribute 'buttontext' = text in the button
*/
class StzhMkCallToAction extends HTMLElement {

    static get observedAttribtes() {
        return ['destination', 'buttontext'];
    }

    constructor() { 
        super();
        this.shadow = this.attachShadow({mode: 'open'});
    }

    connectedCallback() { 
        if (this.isConnected){
            this.loadTemplateToShadow(this.shadow, 'StzhMkCallToAction.html')
                .then(r => {
                    this.anchor.href = this.destination;
                    this.anchor.textContent = this.buttontext;        
                });
        }
    }

    loadTemplateToShadow(targetElem, fileName){
        return fetch(fileName)
            .then(r => r.text())
            .then(template => {
                targetElem.innerHTML = template;
            }
        );
    }

    get anchor () {
        return this.shadowRoot.querySelector('a');
    }

    get destination () {
        return this.getAttribute('destination');
    }

    set destination(value){
        if ('string' === typeof value) {
            this.setAttribute('destination', value);
        }
    }

    get buttontext () {
        return this.getAttribute('buttontext');
    }

    set buttontext(value){
        if ('string' === typeof value) {
            this.setAttribute('buttontext', value);
        }
    }

    attributeChangedCallback(attrName, oldValue, newValue) {
        switch (attrName) {
          case 'destination':
            this.anchor.href = newValue;
            break;
          case 'buttontext':
            this.anchor.textContent = newValue;
            break;
         }
        
      }

}

customElements.define('stzh-mk-call-to-action', StzhMkCallToAction);


