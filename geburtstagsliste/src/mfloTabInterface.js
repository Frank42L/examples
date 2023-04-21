// import { html, render } from './lit-html.js';


const templateNode = document.createElement('template');

class MfloTabInterface extends HTMLElement {
  constructor() { 
      super();
      /* attachShadow works only on certain tags - see https://developer.mozilla.org/en-US/docs/Web/API/Element/attachShadow */
      this.shadow = this.attachShadow({mode: 'open'});
      this.nmbrTabs = 0;
  }

  connectedCallback() { 
      if (this.isConnected){
          this.loadTemplateToShadow(this.shadow, 'MfloTabInterface.html');
       }
  }

  loadTemplateToShadow(targetElem, fileName){
      fetch(fileName)
          .then(r => r.text())
          .then(template => {
              targetElem.innerHTML = template;
          })
          .then(x => {
            this.addPanelSemanticsToContainer();
            this.addPanelSemanticsToPanels();
            this.activateFirstTab();
          });
  }

  getParentTabList() {
    return this.shadow.querySelector('ul');
  }

  getTabs() {
    return this.getParentTabList().querySelectorAll('a');
  }

  getPanels() {
    return this.querySelectorAll("mflo-tabinterface-panel");
  }

  getPanel(pos) {
    var panels = this.getPanels();
    var panel = panels.item(pos-1);
    /*console.log("pos= " + pos);
    console.log("returned child pos=" + pos + ", panel.pos= " + panel.tabPosition + " , id=" + panel.id + ", title="+ panel.title);*/
    return panel;
  }

  
  addPanelToTabList(pos, title) {
    var tablist = this.getParentTabList();
    var panelListEntry = null;
    var referenceToAdd = null;
    var slotToAdd = null;
    var panelName = null;

    /* generate following content
        <li>
            <a href="#section1"><slot name="title">[Section-1]</slot></a>
        </li>
    */
    panelName = "panel" + pos;

    panelListEntry = document.createElement('li');
    panelListEntry.setAttribute('role', 'presentation');
    tablist.appendChild(panelListEntry);
    tablist.setAttribute('role', 'tablist');
    
    referenceToAdd = document.createElement('a');
    referenceToAdd.setAttribute('href', '#' + panelName);
    referenceToAdd.setAttribute('role', 'tab');
    referenceToAdd.setAttribute('id', 'tab' + pos);
    referenceToAdd.id = 'tab' + pos;
    referenceToAdd.setAttribute('pos', pos);
    referenceToAdd.setAttribute('tabindex', '-1');
    referenceToAdd.textContent = title;

    // Handle clicking of tabs for mouse users
    referenceToAdd.addEventListener('click', e => {
      e.preventDefault();
      let currentTab = tablist.querySelector('[aria-selected]');
      if (e.currentTarget !== currentTab) {
        this.switchTab(currentTab, e.currentTarget);
      }
    });

    // Handle arrows
    referenceToAdd.addEventListener('keydown', e => {
      // Get the index of the current tab in the tabs node list
      var currentTab = e.currentTarget; 
      var pos = +currentTab.getAttribute('pos');
      var tabs = this.getTabs();
      /* let index = Array.prototype.indexOf.call(tabs, e.currentTarget); */
      // Work out which key the user is pressing and
      // Calculate the new tab's index where appropriate
      let newPos = e.which === 37 ? pos - 1 : e.which === 39 ? pos + 1 : e.which === 40 ? 'down' : null;
      newPos = null;
      if (e.which === 37) {
        newPos = pos - 1;
      } else if (e.which === 39) {
        newPos = pos + 1;
      } else if (e.which === 40) {
        newPos = 'down';
      }
      if (newPos !== null) {
        e.preventDefault();
        // If the down key is pressed, move focus to the open panel,
        // otherwise switch to the adjacent tab
        if (newPos === 'down') {
          var panel = this.getPanel(pos);
          panel.focus();
        } else if (newPos > 0) {
          this.switchTab(e.currentTarget, tabs[newPos-1])
        }
      }
    });
    panelListEntry.appendChild(referenceToAdd);
  }

  addPanelSemanticsToContainer() {
    var tablist = this.getParentTabList();
    tablist.setAttribute('role', 'tablist');
  }

  addPanelSemanticsToPanels(){
    var panels = this.getPanels();
    panels.forEach( function(panel, i) {
      var pos = i+1;
      var id = "panel" + pos;
      var newTitle;

      // console.log("pos = " + pos + " panel " + panel.title);

      newTitle = "NEWTITLE";
      newTitle = panel.title;
      this.addPanelToTabList(pos, newTitle);

      const tabs = this.getTabs();
      panel.setAttribute('pos', pos);
      panel.setAttribute('role', 'tabpanel');
      panel.setAttribute('tabindex', '-1'); 
      panel.setAttribute('id', id); 
      panel.setAttribute('aria-labelledby', tabs[i].getAttribute('id'));
      panel.hidden = true; 
      panel.display = 'none';
      /*console.log("ADDED PANEL " + panel.getAttribute('pos') 
        + ", id=" + panel.getAttribute('id') 
        + ", title="+ panel.title);*/
    }, this);
  }

  activateFirstTab() {
    var i = 0;
    var panel = null;
    const tabs = this.getTabs();
    //    const panels = this.shadow.querySelectorAll('[id^="section"]');

    // Initially activate the first tab and reveal the first tab panel
    for (let pos = 1; pos <= tabs.length; pos++) {
      if (pos == 1) {
        this.getPanel(pos).hidden = false;
        this.getPanel(pos).style.display = 'block';
      } else {
        this.getPanel(pos).hidden = true;
        this.getPanel(pos).style.display = 'none';
      }
    }

    tabs[0].removeAttribute('tabindex');
    tabs[0].setAttribute('aria-selected', 'true');
  } 

  // The tab switching function
  switchTab(oldTab, newTab) {
    const tabs = this.getTabs();
    var panel;

    newTab.focus();
    // Make the active tab focusable by the user (Tab key)
    newTab.removeAttribute('tabindex');
    // Set the selected state
    newTab.setAttribute('aria-selected', 'true');
    oldTab.removeAttribute('aria-selected');
    oldTab.setAttribute('tabindex', '-1');
    // Get the indices of the new and old tabs to find the correct
    // tab panels to show and hide

    var oldPosition = oldTab.id.substring(3);
    var newPosition = newTab.id.substring(3);
    this.getPanel(oldPosition).hidden = true;
    this.getPanel(newPosition).hidden = false;
    this.getPanel(oldPosition).style.display = 'none';
    this.getPanel(newPosition).style.display = 'block';
  }
}

customElements.define('mflo-tabinterface', MfloTabInterface);
