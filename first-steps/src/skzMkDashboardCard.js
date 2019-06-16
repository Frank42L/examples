// import { html, render } from './lit-html.js';


const templateNode = document.createElement('template');


class StzhMkDashboardCard extends HTMLElement {
    constructor() { 
        super();
        /* attachShadow works only on certain tags - see https://developer.mozilla.org/en-US/docs/Web/API/Element/attachShadow */
        this.shadow = this.attachShadow({mode: 'open'});
    }

    connectedCallback() { 
        if (this.isConnected){
            this.loadTemplateToShadow(this.shadow, 'StzhMkDashboardCard.html');
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

customElements.define('stzh-mk-dashboard-card', StzhMkDashboardCard);


/* TODO : Duplizieren der TemplateNodes für Performanmz */
/*
     connectedCallback() { 
        if (this.isConnected){
            StzhMkDashboardCard.getTemplate()
                .then(template => {
                    StzhMkDashboardCard.duplicateChildNodes(template, 
                        this.shadowRoot);
                    console.log(templateNode.innerHTML);
                    console.log(this.shadowRoot.innerHTML);
                });
        }
    }

    static getTemplate() {
        return fetch('StzhMkDashboardCard.html')
            .then(r => r.text())
            .then(template => {
                templateNode.innerHTML = template;
                return templateNode;
            });
    }
 */





