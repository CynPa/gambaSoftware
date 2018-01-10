/*   1:    */ package com.asinfo.as2.finaciero.activos.configuracion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   4:    */ import com.asinfo.as2.entities.ActivoFijo;
/*   5:    */ import com.asinfo.as2.financiero.activos.configuracion.servicio.ServicioActivoFijo;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.HashMap;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Map;
/*  10:    */ import javax.annotation.PostConstruct;
/*  11:    */ import javax.ejb.EJB;
/*  12:    */ import javax.faces.bean.ManagedBean;
/*  13:    */ import javax.faces.bean.ViewScoped;
/*  14:    */ import org.primefaces.component.datatable.DataTable;
/*  15:    */ import org.primefaces.model.LazyDataModel;
/*  16:    */ import org.primefaces.model.SortOrder;
/*  17:    */ 
/*  18:    */ @ManagedBean
/*  19:    */ @ViewScoped
/*  20:    */ public class ListaActivoFijoBean
/*  21:    */   extends PageControllerAS2
/*  22:    */ {
/*  23:    */   private static final long serialVersionUID = -4639344140036522103L;
/*  24:    */   @EJB
/*  25:    */   private ServicioActivoFijo servicioActivoFijo;
/*  26:    */   private LazyDataModel<ActivoFijo> listaActivoFijo;
/*  27:    */   private DataTable dtActivoFijo;
/*  28:    */   
/*  29:    */   @PostConstruct
/*  30:    */   public void init()
/*  31:    */   {
/*  32: 65 */     this.listaActivoFijo = new LazyDataModel()
/*  33:    */     {
/*  34:    */       private static final long serialVersionUID = 1L;
/*  35:    */       
/*  36:    */       public List<ActivoFijo> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  37:    */       {
/*  38: 72 */         List<ActivoFijo> lista = new ArrayList();
/*  39: 73 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  40: 75 */         if (filters == null) {
/*  41: 76 */           filters = new HashMap();
/*  42:    */         }
/*  43: 78 */         filters.put("activo", "true");
/*  44:    */         
/*  45: 80 */         lista = ListaActivoFijoBean.this.servicioActivoFijo.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  46:    */         
/*  47: 82 */         ListaActivoFijoBean.this.listaActivoFijo.setRowCount(ListaActivoFijoBean.this.servicioActivoFijo.contarPorCriterio(filters));
/*  48:    */         
/*  49: 84 */         return lista;
/*  50:    */       }
/*  51:    */     };
/*  52:    */   }
/*  53:    */   
/*  54:    */   public LazyDataModel<ActivoFijo> getListaActivoFijo()
/*  55:    */   {
/*  56: 99 */     return this.listaActivoFijo;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void setListaActivoFijo(LazyDataModel<ActivoFijo> listaActivoFijo)
/*  60:    */   {
/*  61:109 */     this.listaActivoFijo = listaActivoFijo;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public DataTable getDtActivoFijo()
/*  65:    */   {
/*  66:118 */     return this.dtActivoFijo;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void setDtActivoFijo(DataTable dtActivoFijo)
/*  70:    */   {
/*  71:128 */     this.dtActivoFijo = dtActivoFijo;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public String editar()
/*  75:    */   {
/*  76:138 */     return null;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public String guardar()
/*  80:    */   {
/*  81:148 */     return null;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public String eliminar()
/*  85:    */   {
/*  86:158 */     return null;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public String limpiar()
/*  90:    */   {
/*  91:168 */     return null;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public String cargarDatos()
/*  95:    */   {
/*  96:178 */     return null;
/*  97:    */   }
/*  98:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.activos.configuracion.ListaActivoFijoBean
 * JD-Core Version:    0.7.0.1
 */