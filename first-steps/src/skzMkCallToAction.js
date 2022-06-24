// import { html, render } from './lit-html.js';


const templateNode = document.createElement('template');


class StzhMkCallToAction extends HTMLElement {
    constructor() { 
        super();
        this.shadow = this.attachShadow({mode: 'open'});
    }

    connectedCallback() { 
        if (this.isConnected){
            this.shadow.innerHTML = `
                <style>@import url("assets/css/style.css");</style>
                <style>@import url("assets/css/style_mk.css");</style>
                <style>@import url("assets/css/rwrd_simulation.css");</style>
                <div class="stzh-mk-call-to-action">
                    <a href="${this.href}"><slot>[CTA_TEXT]</slot></a>
                </div>
            `;
        }
    }

    set href(hrefContent) { 
        this.setAttribute('href', hrefContent);
    }
    
    get href() { 
        return this.getAttribute('href');
    }

}

customElements.define('stzh-mk-call-to-action', StzhMkCallToAction);

