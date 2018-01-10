package com.asinfo.as2.amortizacion.configuracion.controller;

import com.asinfo.as2.controller.LanguageController;
import com.asinfo.as2.controller.PageControllerAS2;
import com.asinfo.as2.entities.Organizacion;
import com.asinfo.as2.entities.Sucursal;
import com.asinfo.as2.entities.amortizacion.TipoAmortizacion;
import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.servicio.ServicioGenerico;
import com.asinfo.as2.util.AppUtil;
import com.asinfo.as2.utils.JsfUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import org.apache.log4j.Logger;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

@ManagedBean
@ViewScoped
public class TipoAmortizacionBean
  extends PageControllerAS2
{
  private static final long serialVersionUID = 1L;
  @EJB
  private ServicioGenerico<TipoAmortizacion> servicioTipoAmortizacion;
  private TipoAmortizacion tipoAmortizacion;
  private LazyDataModel<TipoAmortizacion> listaTipoAmortizacion;
  private List<SelectItem> tipoAmortizacionItems;
  private DataTable dtTipoAmortizacion;
  
  @PostConstruct
  public void init()
  {
    this.listaTipoAmortizacion = new LazyDataModel()
    {
      private static final long serialVersionUID = 1L;
      
      public List<TipoAmortizacion> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
      {
        List<TipoAmortizacion> lista = new ArrayList();
        boolean ordenar = sortOrder == SortOrder.ASCENDING;
        
        lista = TipoAmortizacionBean.this.servicioTipoAmortizacion.obtenerListaPorPagina(TipoAmortizacion.class, startIndex, pageSize, sortField, ordenar, filters);
        TipoAmortizacionBean.this.listaTipoAmortizacion.setRowCount(TipoAmortizacionBean.this.servicioTipoAmortizacion.contarPorCriterio(TipoAmortizacion.class, filters));
        
        return lista;
      }
    };
  }
  
  public String editar()
  {
    if (getTipoAmortizacion().getId() > 0) {
      setEditado(true);
    } else {
      addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
    }
    return "";
  }
  
  public String guardar()
  {
    try
    {
      this.servicioTipoAmortizacion.guardarValidar(this.tipoAmortizacion);
      limpiar();
      setEditado(false);
      addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
    }
    catch (AS2Exception e)
    {
      JsfUtil.addErrorMessage(e, "");
    }
    catch (Exception e)
    {
      addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
      LOG.error("ERROR AL GUARDAR DATOS", e);
    }
    return "";
  }
  
  public String limpiar()
  {
    crearTipoAmortizacion();
    return "";
  }
  
  public String eliminar()
  {
    try
    {
      this.servicioTipoAmortizacion.eliminar(this.tipoAmortizacion);
      addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
    }
    catch (Exception e)
    {
      addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
      LOG.error("ERROR AL ELIMINAR DATOS", e);
    }
    return "";
  }
  
  public String cargarDatos()
  {
    return "";
  }
  
  public void cargarDatosTipoAmortizacion()
  {
    List<TipoAmortizacion> tipoAmortizaciones = new ArrayList();
    tipoAmortizaciones = this.servicioTipoAmortizacion.obtenerListaCombo(TipoAmortizacion.class, "nombre", true, null);
    this.tipoAmortizacionItems = new ArrayList();
    for (TipoAmortizacion TipoAmortizacionX : tipoAmortizaciones)
    {
      int value = TipoAmortizacionX.getIdTipoAmortizacion();
      String label = TipoAmortizacionX.getNombre();
      SelectItem opcion = new SelectItem(Integer.valueOf(value), label);
      this.tipoAmortizacionItems.add(opcion);
    }
  }
  
  public void crearTipoAmortizacion()
  {
    this.tipoAmortizacion = new TipoAmortizacion();
    this.tipoAmortizacion.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
    this.tipoAmortizacion.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
    this.tipoAmortizacion.setActivo(true);
  }
  
  public void onRowSelect(SelectEvent event)
  {
    TipoAmortizacion tipoAmortizacion1 = (TipoAmortizacion)event.getObject();
    setTipoAmortizacion(tipoAmortizacion1);
  }
  
  public TipoAmortizacion getTipoAmortizacion()
  {
    if (this.tipoAmortizacion == null) {
      crearTipoAmortizacion();
    }
    return this.tipoAmortizacion;
  }
  
  public void setTipoAmortizacion(TipoAmortizacion TipoAmortizacion)
  {
    this.tipoAmortizacion = TipoAmortizacion;
  }
  
  public LazyDataModel<TipoAmortizacion> getListaTipoAmortizacion()
  {
    return this.listaTipoAmortizacion;
  }
  
  public void setListaTipoAmortizacion(LazyDataModel<TipoAmortizacion> listaTipoAmortizacion)
  {
    this.listaTipoAmortizacion = listaTipoAmortizacion;
  }
  
  public List<SelectItem> getTipoAmortizacionItems()
  {
    return this.tipoAmortizacionItems;
  }
  
  public void setTipoAmortizacionItems(List<SelectItem> TipoAmortizacionItems)
  {
    this.tipoAmortizacionItems = TipoAmortizacionItems;
  }
  
  public DataTable getDtTipoAmortizacion()
  {
    return this.dtTipoAmortizacion;
  }
  
  public void setDtTipoAmortizacion(DataTable dtTipoAmortizacion)
  {
    this.dtTipoAmortizacion = dtTipoAmortizacion;
  }
}
