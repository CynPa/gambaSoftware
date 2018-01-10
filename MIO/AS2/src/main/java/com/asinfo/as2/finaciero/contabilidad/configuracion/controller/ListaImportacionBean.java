/*   1:    */ package com.asinfo.as2.finaciero.contabilidad.configuracion.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compras.importaciones.procesos.servicio.ServicioFacturaProveedorImportacion;
/*   4:    */ import com.asinfo.as2.controller.PageController;
/*   5:    */ import com.asinfo.as2.entities.FacturaProveedorImportacion;
/*   6:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   7:    */ import java.util.ArrayList;
/*   8:    */ import java.util.HashMap;
/*   9:    */ import java.util.List;
/*  10:    */ import java.util.Map;
/*  11:    */ import javax.annotation.PostConstruct;
/*  12:    */ import javax.ejb.EJB;
/*  13:    */ import javax.faces.bean.ManagedBean;
/*  14:    */ import javax.faces.bean.SessionScoped;
/*  15:    */ import org.primefaces.context.RequestContext;
/*  16:    */ import org.primefaces.model.LazyDataModel;
/*  17:    */ import org.primefaces.model.SortOrder;
/*  18:    */ 
/*  19:    */ @ManagedBean
/*  20:    */ @SessionScoped
/*  21:    */ public class ListaImportacionBean
/*  22:    */   extends PageController
/*  23:    */ {
/*  24:    */   private static final long serialVersionUID = 6583450793826204104L;
/*  25:    */   @EJB
/*  26:    */   private ServicioFacturaProveedorImportacion servicioFacturaProveedorImportacion;
/*  27:    */   private LazyDataModel<FacturaProveedorImportacion> listaFacturaProveedorImportacion;
/*  28:    */   private Estado estado;
/*  29:    */   
/*  30:    */   @PostConstruct
/*  31:    */   public void init()
/*  32:    */   {
/*  33: 64 */     this.listaFacturaProveedorImportacion = new LazyDataModel()
/*  34:    */     {
/*  35:    */       private static final long serialVersionUID = 1L;
/*  36:    */       
/*  37:    */       public List<FacturaProveedorImportacion> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  38:    */       {
/*  39: 72 */         List<FacturaProveedorImportacion> lista = new ArrayList();
/*  40:    */         
/*  41: 74 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  42: 76 */         if (ListaImportacionBean.this.estado != null) {
/*  43: 77 */           filters.put("facturaProveedor.estado", ListaImportacionBean.this.estado.toString());
/*  44:    */         } else {
/*  45: 79 */           filters.put("facturaProveedor.estado", Estado.ELABORADO.toString());
/*  46:    */         }
/*  47: 82 */         lista = ListaImportacionBean.this.servicioFacturaProveedorImportacion.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  48: 83 */         ListaImportacionBean.this.listaFacturaProveedorImportacion.setRowCount(ListaImportacionBean.this.servicioFacturaProveedorImportacion.contarPorCriterio(filters));
/*  49: 84 */         return lista;
/*  50:    */       }
/*  51:    */     };
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void seleccionarImportacion(FacturaProveedorImportacion importacion)
/*  55:    */   {
/*  56: 90 */     RequestContext.getCurrentInstance().closeDialog(importacion);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void listarImportaciones()
/*  60:    */   {
/*  61: 95 */     Map<String, Object> properties = new HashMap();
/*  62: 96 */     properties.put("modal", Boolean.valueOf(true));
/*  63: 97 */     properties.put("resizable", Boolean.valueOf(false));
/*  64: 98 */     properties.put("draggable", Boolean.valueOf(true));
/*  65: 99 */     properties.put("contentWidth", Integer.valueOf(1200));
/*  66:100 */     properties.put("contentHeight", Integer.valueOf(440));
/*  67:101 */     properties.put("height", Integer.valueOf(450));
/*  68:102 */     RequestContext.getCurrentInstance().openDialog("/resources/componentes/seleccionarImportacion", properties, null);
/*  69:    */   }
/*  70:    */   
/*  71:    */   public ServicioFacturaProveedorImportacion getServicioFacturaProveedorImportacion()
/*  72:    */   {
/*  73:106 */     return this.servicioFacturaProveedorImportacion;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void setServicioFacturaProveedorImportacion(ServicioFacturaProveedorImportacion servicioFacturaProveedorImportacion)
/*  77:    */   {
/*  78:110 */     this.servicioFacturaProveedorImportacion = servicioFacturaProveedorImportacion;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public LazyDataModel<FacturaProveedorImportacion> getListaFacturaProveedorImportacion()
/*  82:    */   {
/*  83:114 */     return this.listaFacturaProveedorImportacion;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void setListaFacturaProveedorImportacion(LazyDataModel<FacturaProveedorImportacion> listaFacturaProveedorImportacion)
/*  87:    */   {
/*  88:118 */     this.listaFacturaProveedorImportacion = listaFacturaProveedorImportacion;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public Estado getEstado()
/*  92:    */   {
/*  93:122 */     return this.estado;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void setEstado(Estado estado)
/*  97:    */   {
/*  98:126 */     this.estado = estado;
/*  99:    */   }
/* 100:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.contabilidad.configuracion.controller.ListaImportacionBean
 * JD-Core Version:    0.7.0.1
 */