// import { html, render } from './lit-html.js';

class StzhMkWidgetCanvas extends HTMLElement { 

    constructor() { 
        super();
        var shadow = this.attachShadow({mode: 'open'});
        
        if (this.hasAttribute('title')) {
            this.widgetTitle = this.getAttribute('title');
        } else {
            this.widgetTitle = '<NO TITLE>';
        }

        if (this.hasAttribute('subTitle')) {
            this.subTitle = this.getAttribute('subTitle');
        } else {
            this.subTitle = '<NO SUB-TITLE>';
        }

        this.messageCounter = 3;
        this.items = [1,2,3];

        this.createTemplate(shadow);
    }

    createTemplate(shadow) {
        var wrapper = document.createElement('span');
        wrapper.setAttribute('class', 'stzhMkWidgetTitle');
        wrapper.textContent = this.widgetTitle;
        wrapper.setAttribute('class', 'stzhMkWidgetCanvas');
        
        var countMarker = document.createElement('span');
        countMarker.setAttribute('class', 'stzhMkCountMarker');
        countMarker.setAttribute('tabIndex', 0);

        var info = document.createElement('span');
        info.setAttribute('class', 'stzhMkInfo');
        info.textContent = this.subTitle;

        var imgUrl = 'img/info_icon.png';
        var img = document.createElement('img');
        img.src = imgUrl;
        countMarker.appendChild(img);

        var inhalt = document.createElement('span');
        inhalt.setAttribute('class', 'stzhMkWidgetInhalt');
        inhalt.textContent = this.textContent;

        var style = document.createElement('style');
        style.textContent = '@import url("rwrd_simulation.css");';
        shadow.appendChild(style);
        shadow.appendChild(wrapper);
        wrapper.appendChild(countMarker);
        wrapper.appendChild(info);
        wrapper.appendChild(inhalt);

    }
}

customElements.define('stzh-mk-widget-canvas', StzhMkWidgetCanvas);



