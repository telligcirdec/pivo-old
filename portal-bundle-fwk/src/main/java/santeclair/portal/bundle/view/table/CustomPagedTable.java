package santeclair.portal.bundle.view.table;

import java.util.ArrayList;
import java.util.List;

import santeclair.portal.bundle.utils.Ie8CssFontHack;

import com.vaadin.data.Container;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Tableau avec la gestion de la pagination.
 * Le composant de pagination est un HorizontalLayout qui se récupère avec createControls()
 * Se base sur l'addon vaadin PagedTable
 * 
 * @author tsensebe
 * 
 */
public class CustomPagedTable extends Table {

    private static final long serialVersionUID = 1L;

    private Button firstBtn;
    private Button previousBtn;
    private Button nextBtn;
    private Button lastBtn;

    private HorizontalLayout controlBar;

    private Label pagesCount;

    public interface PageChangeListener {
        public void pageChanged(PagedTableChangeEvent event);
    }

    public class PagedTableChangeEvent {

        final CustomPagedTable table;

        public PagedTableChangeEvent(CustomPagedTable table) {
            this.table = table;
        }

        public CustomPagedTable getTable() {
            return table;
        }

        public int getCurrentPage() {
            return table.getCurrentPage();
        }

        public int getTotalAmountOfPages() {
            return table.getTotalAmountOfPages();
        }
    }

    private List<PageChangeListener> listeners = null;

    private PagedTableContainer container;

    public CustomPagedTable() {
        this(null);
    }

    public CustomPagedTable(String caption) {
        super(caption);
        setPageLength(25);
        addStyleName("pagedtable");
    }

    public HorizontalLayout createControls() {
        initFirstButton();
        initPreviousButton();
        initNextButton();
        initLastButton();
        initLayout();
        return controlBar;
    }

    private void initPageCount() {
        if (pagesCount != null) {
            controlBar.removeComponent(pagesCount);
        }
        pagesCount = new Label(getItemIds().size() + " Lignes, page " + getCurrentPage() + " / " + getTotalAmountOfPages());
        controlBar.addComponent(pagesCount, 0);
        controlBar.setComponentAlignment(pagesCount, Alignment.MIDDLE_LEFT);
    }

    private void initFirstButton() {
        firstBtn = new Button("<<", new ClickListener() {
            private static final long serialVersionUID = -355520120491283992L;

            public void buttonClick(ClickEvent event) {
                setCurrentPage(0);
                initPageCount();
                Ie8CssFontHack.showFonts();
            }
        });
        firstBtn.setVisible(((PagedTableContainer) getContainerDataSource()).getStartIndex() > 0);
        addListener(new PageChangeListener() {
            public void pageChanged(PagedTableChangeEvent event) {
                firstBtn.setVisible(((PagedTableContainer) getContainerDataSource()).getStartIndex() > 0);
            }
        });
        firstBtn.setStyleName(ValoTheme.BUTTON_FRIENDLY);
    }

    private void initPreviousButton() {
        previousBtn = new Button("<", new ClickListener() {
            private static final long serialVersionUID = -355520120491283992L;

            public void buttonClick(ClickEvent event) {
                previousPage();
                initPageCount();
                Ie8CssFontHack.showFonts();
            }
        });
        previousBtn.setVisible(((PagedTableContainer) getContainerDataSource()).getStartIndex() > 0);
        addListener(new PageChangeListener() {
            public void pageChanged(PagedTableChangeEvent event) {
                previousBtn.setVisible(((PagedTableContainer) getContainerDataSource()).getStartIndex() > 0);
            }
        });
        previousBtn.setStyleName(ValoTheme.BUTTON_FRIENDLY);
    }

    private void initNextButton() {
        nextBtn = new Button(">", new ClickListener() {
            private static final long serialVersionUID = -1927138212640638452L;

            public void buttonClick(ClickEvent event) {
                nextPage();
                initPageCount();
                Ie8CssFontHack.showFonts();
            }
        });
        nextBtn.setVisible(((PagedTableContainer) getContainerDataSource()).getStartIndex() < ((PagedTableContainer) getContainerDataSource())
                        .getRealSize() - getPageLength());
        addListener(new PageChangeListener() {
            public void pageChanged(PagedTableChangeEvent event) {
                nextBtn.setVisible(((PagedTableContainer) getContainerDataSource())
                                .getStartIndex() < ((PagedTableContainer) getContainerDataSource())
                                .getRealSize() - getPageLength());
            }
        });
        nextBtn.setStyleName(ValoTheme.BUTTON_FRIENDLY);
    }

    private void initLastButton() {
        lastBtn = new Button(">>", new ClickListener() {
            private static final long serialVersionUID = -355520120491283992L;

            public void buttonClick(ClickEvent event) {
                setCurrentPage(getTotalAmountOfPages());
                initPageCount();
                Ie8CssFontHack.showFonts();
            }
        });
        lastBtn.setVisible(((PagedTableContainer) getContainerDataSource()).getStartIndex() < ((PagedTableContainer) getContainerDataSource())
                        .getRealSize() - getPageLength());
        addListener(new PageChangeListener() {
            public void pageChanged(PagedTableChangeEvent event) {
                lastBtn.setVisible(((PagedTableContainer) getContainerDataSource())
                                .getStartIndex() < ((PagedTableContainer) getContainerDataSource())
                                .getRealSize() - getPageLength());
            }
        });
        lastBtn.setStyleName(ValoTheme.BUTTON_FRIENDLY);
    }

    private void initLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setSpacing(true);
        buttonLayout.addComponent(firstBtn);
        buttonLayout.addComponent(previousBtn);
        buttonLayout.addComponent(nextBtn);
        buttonLayout.addComponent(lastBtn);

        controlBar = new HorizontalLayout();
        controlBar.setMargin(true);
        controlBar.setSizeFull();
        initPageCount();
        controlBar.addComponent(buttonLayout);
        controlBar.setComponentAlignment(buttonLayout, Alignment.MIDDLE_RIGHT);
        firstBtn.click();
    }

    @Override
    public Container.Indexed getContainerDataSource() {
        return container;
    }

    @Override
    public void setContainerDataSource(Container newDataSource) {
        if (!(newDataSource instanceof Container.Indexed)) {
            throw new IllegalArgumentException(
                            "PagedTable can only use containers that implement Container.Indexed");
        }
        PagedTableContainer pagedTableContainer = new PagedTableContainer(
                        (Container.Indexed) newDataSource);
        pagedTableContainer.setPageLength(getPageLength());
        super.setContainerDataSource(pagedTableContainer);
        this.container = pagedTableContainer;
        firePagedChangedEvent();
    }

    private void setPageFirstIndex(int firstIndex) {
        if (container != null) {
            if (firstIndex <= 0) {
                firstIndex = 0;
            }
            if (firstIndex > container.getRealSize() - 1) {
                int size = container.getRealSize() - 1;
                int pages = 0;
                if (getPageLength() != 0) {
                    pages = (int) Math.floor(0.0 + size / getPageLength());
                }
                firstIndex = pages * getPageLength();
            }
            container.setStartIndex(firstIndex);
            setCurrentPageFirstItemIndex(firstIndex);
            containerItemSetChange(new Container.ItemSetChangeEvent() {
                private static final long serialVersionUID = -5083660879306951876L;

                public Container getContainer() {
                    return container;
                }
            });
            if (alwaysRecalculateColumnWidths) {
                for (Object columnId : container.getContainerPropertyIds()) {
                    setColumnWidth(columnId, -1);
                }
            }
            firePagedChangedEvent();
        }
    }

    private void firePagedChangedEvent() {
        if (listeners != null) {
            PagedTableChangeEvent event = new PagedTableChangeEvent(this);
            for (PageChangeListener listener : listeners) {
                listener.pageChanged(event);
            }
        }
    }

    @Override
    public void setPageLength(int pageLength) {
        if (pageLength >= 0 && getPageLength() != pageLength) {
            container.setPageLength(pageLength);
            super.setPageLength(pageLength);
            firePagedChangedEvent();
        }
    }

    public void nextPage() {
        setPageFirstIndex(container.getStartIndex() + getPageLength());
    }

    public void previousPage() {
        setPageFirstIndex(container.getStartIndex() - getPageLength());
    }

    public int getCurrentPage() {
        double pageLength = getPageLength();
        int page = (int) Math.floor(container.getStartIndex()
                        / pageLength) + 1;
        if (page < 1) {
            page = 1;
        }
        return page;
    }

    public void setCurrentPage(int page) {
        int newIndex = (page - 1) * getPageLength();
        if (newIndex < 0) {
            newIndex = 0;
        }
        if (newIndex >= 0 && newIndex != container.getStartIndex()) {
            setPageFirstIndex(newIndex);
        }
    }

    public int getTotalAmountOfPages() {
        int size = container.getContainer().size();
        double pageLength = getPageLength();
        int pageCount = (int) Math.ceil(size / pageLength);
        if (pageCount < 1) {
            pageCount = 1;
        }
        return pageCount;
    }

    public void addListener(PageChangeListener listener) {
        if (listeners == null) {
            listeners = new ArrayList<PageChangeListener>();
        }
        listeners.add(listener);
    }

    public void removeListener(PageChangeListener listener) {
        if (listeners == null) {
            listeners = new ArrayList<PageChangeListener>();
        }
        listeners.remove(listener);
    }

    public void setAlwaysRecalculateColumnWidths(
                    boolean alwaysRecalculateColumnWidths) {
        this.alwaysRecalculateColumnWidths = alwaysRecalculateColumnWidths;
    }

}
