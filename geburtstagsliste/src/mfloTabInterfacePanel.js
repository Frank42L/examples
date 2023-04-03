// import { html, render } from './lit-html.js';


const templateNode = document.createElement('template');

class MfloTabInterfacePanel extends HTMLElement {
    constructor() { 
        super();
        /* attachShadow works only on certain tags - see https://developer.mozilla.org/en-US/docs/Web/API/Element/attachShadow */
        this.shadow = this.attachShadow({mode: 'open'});

        if (this.hasAttribute('title')) {
            this.tabTitle = this.getAttribute('title');
        } else {
            this.tabTitle = '<NO TITLE>';
        }
    }

    getId() {
        return this.id;
    }

    setId(id) {
        this.id = id;
    }
  
    getTitle() {
        return this.title;
    }

    setTitle(title) {
        this.title = title;
    }

    connectedCallback() { 
        if (this.isConnected){
            this.loadTemplateToShadow(this.shadow, 'MfloTabInterfacePanel.html');
        }
    }

    loadTemplateToShadow(targetElem, fileName){
        fetch(fileName)
            .then(r => r.text())
            .then(template => {
                targetElem.innerHTML = template;
            });
    }

    getParentTabList() {
        var parentTabList = this.parentElement.querySelector('ul');
        console.log(parentTabList);
        if (parentTabList === null) {
            console.log("parentTabList not there");
            parentTabList = document.createElement('ul');
            this.parentElement.appendChild(parentTabList);
        } else {
            console.log("parentTabList is there");
        }
        return parentTabList;
    }
}


customElements.define('mflo-tabinterface-panel', MfloTabInterfacePanel);
