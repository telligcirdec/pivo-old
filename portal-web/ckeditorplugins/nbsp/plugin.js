CKEDITOR.plugins.add('nbsp',
{
    init: function(editor) {
        editor.addCommand('insertNbsp', {
            exec : function(editor) {
                editor.insertHtml('&nbsp;');
            }
        });

        editor.ui.addButton('Nbsp', {
            label: 'Insert un espace insécable',
            command: 'insertNbsp',
            icon: this.path + 'images/nbsp.png'
        });

        editor.keystrokeHandler.keystrokes[CKEDITOR.SHIFT + 32 /* SPACE */] = 'insertNbsp';
    }
});