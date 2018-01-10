package com.asinfo.as2.amortizacion.procesos.controller;

import com.asinfo.as2.amortizacion.procesos.servicio.ServicioAmortizacion;
import com.asinfo.as2.compras.procesos.servicio.ServicioFacturaProveedor;
import com.asinfo.as2.controller.LanguageController;
import com.asinfo.as2.controller.PageControllerAS2;
import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
import com.asinfo.as2.entities.Documento;
import com.asinfo.as2.entities.FacturaProveedor;
import com.asinfo.as2.entities.Organizacion;
import com.asinfo.as2.entities.Secuencia;
import com.asinfo.as2.entities.Sucursal;
import com.asinfo.as2.entities.amortizacion.Amortizacion;
import com.asinfo.as2.entities.amortizacion.DetalleAmortizacion;
import com.asinfo.as2.entities.amortizacion.TipoAmortizacion;
import com.asinfo.as2.enumeraciones.DocumentoBase;
import com.asinfo.as2.enumeraciones.Estado;
import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
import com.asinfo.as2.servicio.ServicioGenerico;
import com.asinfo.as2.util.AppUtil;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.log4j.Logger;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

@ManagedBean
@ViewScoped
public class AmortizacionBean
  extends PageControllerAS2
{
  private static final long serialVersionUID = 1L;
  @EJB
  private transient ServicioAmortizacion servicioAmortizacion;
  @EJB
  private transient ServicioDocumento servicioDocumento;
  @EJB
  private transient ServicioGenerico<TipoAmortizacion> servicioTipoAmortizacion;
  @EJB
  private transient ServicioFacturaProveedor servicioFacturaProveedor;
  private DataTable dtAmortizacion;
  private DataTable dtDetalleAmortizacion;
  private Amortizacion amortizacion;
  private LazyDataModel<Amortizacion> listaAmortizacion;
  private List<Documento> listaDocumento;
  private List<TipoAmortizacion> listaTipoAmortizacion;
  
  @PostConstruct
  public void init()
  {
    this.listaAmortizacion = new LazyDataModel()
    {
      private static final long serialVersionUID = 1L;
      
      public List<Amortizacion> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
      {
        List<Amortizacion> lista = new ArrayList();
        boolean ordenar = sortOrder == SortOrder.ASCENDING;
        
        lista = AmortizacionBean.this.servicioAmortizacion.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
        AmortizacionBean.this.listaAmortizacion.setRowCount(AmortizacionBean.this.servicioAmortizacion.contarPorCriterio(filters));
        
        return lista;
      }
    };
  }
  
  public String editar()
  {
    if (getAmortizacion().getId() > 0) {
      try
      {
        this.servicioAmortizacion.esEditable(this.amortizacion);
        this.amortizacion = this.servicioAmortizacion.cargarDetalle(this.amortizacion);
        setEditado(true);
      }
      catch (ExcepcionAS2 e)
      {
        addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
      }
    } else {
      addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
    }
    return "";
  }
  
  public String guardar()
  {
    try
    {
      this.servicioAmortizacion.guardar(this.amortizacion);
      addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
      setEditado(false);
      limpiar();
    }
    catch (ExcepcionAS2Financiero e)
    {
      addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
      LOG.error("ERROR AL GUARDAR DATOS", e);
    }
    catch (AS2Exception e)
    {
      addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
      LOG.error("ERROR AL GUARDAR DATOS", e);
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
    crearAmortizacion();
    return "";
  }
  
  public String eliminar()
  {
    try
    {
      this.servicioAmortizacion.esEditable(this.amortizacion);
      this.amortizacion = this.servicioAmortizacion.cargarDetalle(this.amortizacion);
      this.servicioAmortizacion.eliminar(this.amortizacion);
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
  
  public void crearAmortizacion()
  {
    this.amortizacion = new Amortizacion();
    this.amortizacion.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
    this.amortizacion.setSucursal(AppUtil.getSucursal());
    this.amortizacion.setNumero("");
    this.amortizacion.setFechaRegistro(new Date());
    this.amortizacion.setFechaInicioAmortizacion(new Date());
    this.amortizacion.setValorAmortizado(BigDecimal.ZERO);
    this.amortizacion.setValorAmortizadoTotal(BigDecimal.ZERO);
    this.amortizacion.setMesesAmortizados(0);
    Documento documento = null;
    if ((getListaDocumento() != null) && (!getListaDocumento().isEmpty()))
    {
      documento = (Documento)getListaDocumento().get(0);
      this.amortizacion.setDocumento(documento);
      actualizarDocumento();
    }
    else
    {
      documento = new Documento();
      documento.setSecuencia(new Secuencia());
      this.amortizacion.setDocumento(documento);
    }
  }
  
  public String actualizarDocumento()
  {
    Documento documento = this.servicioDocumento.buscarPorId(Integer.valueOf(this.amortizacion.getDocumento().getId()));
    this.amortizacion.setDocumento(documento);
    
    setSecuenciaEditable(!this.amortizacion.getDocumento().isIndicadorBloqueoSecuencia());
    
    return "";
  }
  
  public void onRowSelect(SelectEvent event)
  {
    Amortizacion Amortizacion1 = (Amortizacion)event.getObject();
    setAmortizacion(Amortizacion1);
  }
  
  public Amortizacion getAmortizacion()
  {
    if (this.amortizacion == null) {
      crearAmortizacion();
    }
    return this.amortizacion;
  }
  
  public void setAmortizacion(Amortizacion Amortizacion)
  {
    this.amortizacion = Amortizacion;
  }
  
  public LazyDataModel<Amortizacion> getListaAmortizacion()
  {
    return this.listaAmortizacion;
  }
  
  public void setListaAmortizacion(LazyDataModel<Amortizacion> listaAmortizacion)
  {
    this.listaAmortizacion = listaAmortizacion;
  }
  
  public DataTable getDtAmortizacion()
  {
    return this.dtAmortizacion;
  }
  
  public void setDtAmortizacion(DataTable dtAmortizacion)
  {
    this.dtAmortizacion = dtAmortizacion;
  }
  
  public DataTable getDtDetalleAmortizacion()
  {
    return this.dtDetalleAmortizacion;
  }
  
  public void setDtDetalleAmortizacion(DataTable dtDetalleAmortizacion)
  {
    this.dtDetalleAmortizacion = dtDetalleAmortizacion;
  }
  
  public List<Documento> getListaDocumento()
  {
    try
    {
      if (this.listaDocumento == null) {
        this.listaDocumento = this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.AMORTIZACION, AppUtil.getOrganizacion()
          .getIdOrganizacion());
      }
    }
    catch (ExcepcionAS2 e)
    {
      addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
    }
    return this.listaDocumento;
  }
  
  public void setListaDocumento(List<Documento> listaDocumento)
  {
    this.listaDocumento = listaDocumento;
  }
  
  public List<TipoAmortizacion> getListaTipoAmortizacion()
  {
    if (this.listaTipoAmortizacion == null) {
      this.listaTipoAmortizacion = this.servicioTipoAmortizacion.obtenerListaCombo(TipoAmortizacion.class, "nombre", true, null);
    }
    return this.listaTipoAmortizacion;
  }
  
  public void setListaTipoAmortizacion(List<TipoAmortizacion> listaTipoAmortizacion)
  {
    this.listaTipoAmortizacion = listaTipoAmortizacion;
  }
  
  public List<FacturaProveedor> autocompletarFacturas(String consulta)
  {
    Map<String, String> filters = new HashMap();
    List<FacturaProveedor> lista = new ArrayList();
    if ((consulta != null) && (!consulta.isEmpty())) {
      filters.put("numero", "%" + consulta + "%");
    }
    filters.put("documento.documentoBase", DocumentoBase.FACTURA_PROVEEDOR.toString());
    filters.put("estado", "!=" + Estado.ANULADO.toString());
    lista = this.servicioFacturaProveedor.obtenerListaCombo("fecha", true, filters);
    
    return lista;
  }
  
  public void calcularDetalleAmortizacion()
  {
    this.servicioAmortizacion.calcularDetalleAmortizacion(this.amortizacion);
  }
  
  public void actualizarFechaCompra()
  {
    if (this.amortizacion.getFacturaProveedor() != null) {
      this.amortizacion.setFechaCompra(this.amortizacion.getFacturaProveedor().getFecha());
    }
  }
  
  public BigDecimal getTotalDetalle()
  {
    BigDecimal total = BigDecimal.ZERO;
    for (DetalleAmortizacion detalle : this.amortizacion.getListaDetalleAmortizacion()) {
      if (!detalle.isEliminado()) {
        total = total.add(detalle.getValor());
      }
    }
    return total;
  }
  
  public void contabilizar()
  {
    try
    {
      this.servicioAmortizacion.contabilizar(AppUtil.getOrganizacion().getIdOrganizacion(), AppUtil.getSucursal().getIdSucursal(), new Date());
      addInfoMessage(getLanguageController().getMensaje("msg_info_dividendos_contabilizados"));
    }
    catch (AS2Exception e)
    {
      addErrorMessage(e.getMensaje());
      LOG.error("ERROR AL CONTABILIZAR", e);
    }
    catch (ExcepcionAS2 e)
    {
      addErrorMessage(e.getMessage());
      LOG.error("ERROR AL CONTABILIZAR", e);
    }
  }
  
  public List<DetalleAmortizacion> getListaDetalleAmortizacion()
  {
    List<DetalleAmortizacion> ldetalle = new ArrayList();
    for (DetalleAmortizacion detalle : this.amortizacion.getListaDetalleAmortizacion()) {
      if (!detalle.isEliminado()) {
        ldetalle.add(detalle);
      }
    }
    return ldetalle;
  }
}
