// import { html, render } from './lit-html.js';


const templateNode = document.createElement('template');


class MfloDashboardCard extends HTMLElement {
    constructor() { 
        super();
        /* attachShadow works only on certain tags - see https://developer.mozilla.org/en-US/docs/Web/API/Element/attachShadow */
        this.shadow = this.attachShadow({mode: 'open'});
    }

    connectedCallback() { 
        if (this.isConnected){
            this.loadTemplateToShadow(this.shadow, 'MfloDashboardCard.html');
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

customElements.define('mflo-dashboard-card', MfloDashboardCard);


/* TODO : Duplizieren der TemplateNodes für Performanmz */
/*
     connectedCallback() { 
        if (this.isConnected){
            MfloDashboardCard.getTemplate()
                .then(template => {
                    MfloDashboardCard.duplicateChildNodes(template, 
                        this.shadowRoot);
                    console.log(templateNode.innerHTML);
                    console.log(this.shadowRoot.innerHTML);
                });
        }
    }

    static getTemplate() {
        return fetch('MfloDashboardCard.html')
            .then(r => r.text())
            .then(template => {
                templateNode.innerHTML = template;
                return templateNode;
            });
    }
 */





