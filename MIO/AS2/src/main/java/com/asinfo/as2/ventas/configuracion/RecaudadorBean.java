/*   1:    */ package com.asinfo.as2.ventas.configuracion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.entities.Recaudador;
/*   7:    */ import com.asinfo.as2.entities.Sucursal;
/*   8:    */ import com.asinfo.as2.util.AppUtil;
/*   9:    */ import com.asinfo.as2.ventas.configuracion.servicio.ServicioRecaudador;
/*  10:    */ import java.util.List;
/*  11:    */ import java.util.Map;
/*  12:    */ import javax.annotation.PostConstruct;
/*  13:    */ import javax.ejb.EJB;
/*  14:    */ import javax.faces.bean.ManagedBean;
/*  15:    */ import javax.faces.bean.ViewScoped;
/*  16:    */ import org.apache.log4j.Logger;
/*  17:    */ import org.primefaces.component.datatable.DataTable;
/*  18:    */ import org.primefaces.model.LazyDataModel;
/*  19:    */ import org.primefaces.model.SortOrder;
/*  20:    */ 
/*  21:    */ @ManagedBean
/*  22:    */ @ViewScoped
/*  23:    */ public class RecaudadorBean
/*  24:    */   extends PageControllerAS2
/*  25:    */ {
/*  26:    */   private static final long serialVersionUID = 7003739604885063308L;
/*  27:    */   @EJB
/*  28:    */   private transient ServicioRecaudador servicioRecaudador;
/*  29:    */   private Recaudador recaudador;
/*  30:    */   private LazyDataModel<Recaudador> listaRecaudador;
/*  31:    */   private DataTable dtRecaudador;
/*  32:    */   
/*  33:    */   @PostConstruct
/*  34:    */   public void init()
/*  35:    */   {
/*  36: 69 */     this.listaRecaudador = new LazyDataModel()
/*  37:    */     {
/*  38:    */       private static final long serialVersionUID = -7452716124816494300L;
/*  39:    */       
/*  40:    */       public List<Recaudador> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  41:    */       {
/*  42: 76 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  43:    */         
/*  44: 78 */         filters.put("idOrganizacion", Integer.toString(AppUtil.getOrganizacion().getIdOrganizacion()));
/*  45: 79 */         List<Recaudador> lista = RecaudadorBean.this.servicioRecaudador.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  46:    */         
/*  47: 81 */         RecaudadorBean.this.listaRecaudador.setRowCount(RecaudadorBean.this.servicioRecaudador.contarPorCriterio(filters));
/*  48: 82 */         return lista;
/*  49:    */       }
/*  50:    */     };
/*  51:    */   }
/*  52:    */   
/*  53:    */   private void crearEntidad()
/*  54:    */   {
/*  55: 97 */     this.recaudador = new Recaudador();
/*  56: 98 */     this.recaudador.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  57: 99 */     this.recaudador.setIdSucursal(AppUtil.getSucursal().getId());
/*  58:    */   }
/*  59:    */   
/*  60:    */   public String editar()
/*  61:    */   {
/*  62:108 */     if (getRecaudador().getIdRecaudador() > 0) {
/*  63:109 */       setEditado(true);
/*  64:    */     } else {
/*  65:111 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  66:    */     }
/*  67:113 */     return "";
/*  68:    */   }
/*  69:    */   
/*  70:    */   public String guardar()
/*  71:    */   {
/*  72:    */     try
/*  73:    */     {
/*  74:123 */       this.servicioRecaudador.guardar(this.recaudador);
/*  75:124 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  76:125 */       setEditado(false);
/*  77:126 */       limpiar();
/*  78:    */     }
/*  79:    */     catch (Exception e)
/*  80:    */     {
/*  81:128 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  82:129 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  83:    */     }
/*  84:131 */     return "";
/*  85:    */   }
/*  86:    */   
/*  87:    */   public String eliminar()
/*  88:    */   {
/*  89:    */     try
/*  90:    */     {
/*  91:141 */       this.servicioRecaudador.eliminar(this.recaudador);
/*  92:142 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/*  93:    */     }
/*  94:    */     catch (Exception e)
/*  95:    */     {
/*  96:144 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/*  97:145 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/*  98:    */     }
/*  99:147 */     return "";
/* 100:    */   }
/* 101:    */   
/* 102:    */   public String cargarDatos()
/* 103:    */   {
/* 104:156 */     return "";
/* 105:    */   }
/* 106:    */   
/* 107:    */   public String limpiar()
/* 108:    */   {
/* 109:165 */     crearEntidad();
/* 110:166 */     return "";
/* 111:    */   }
/* 112:    */   
/* 113:    */   public Recaudador getRecaudador()
/* 114:    */   {
/* 115:182 */     if (this.recaudador == null) {
/* 116:183 */       this.recaudador = new Recaudador();
/* 117:    */     }
/* 118:185 */     return this.recaudador;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void setRecaudador(Recaudador recaudador)
/* 122:    */   {
/* 123:195 */     this.recaudador = recaudador;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public LazyDataModel<Recaudador> getListaRecaudador()
/* 127:    */   {
/* 128:204 */     return this.listaRecaudador;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void setListaRecaudador(LazyDataModel<Recaudador> listaRecaudador)
/* 132:    */   {
/* 133:214 */     this.listaRecaudador = listaRecaudador;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public DataTable getDtRecaudador()
/* 137:    */   {
/* 138:223 */     return this.dtRecaudador;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void setDtRecaudador(DataTable dtRecaudador)
/* 142:    */   {
/* 143:233 */     this.dtRecaudador = dtRecaudador;
/* 144:    */   }
/* 145:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.configuracion.RecaudadorBean
 * JD-Core Version:    0.7.0.1
 */