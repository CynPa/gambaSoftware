/*   1:    */ package com.asinfo.as2.polizas.configuracion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.entities.Sucursal;
/*   7:    */ import com.asinfo.as2.entities.polizas.Asegurado;
/*   8:    */ import com.asinfo.as2.entities.polizas.TipoAsegurado;
/*   9:    */ import com.asinfo.as2.polizas.configuracion.servicio.ServicioAsegurado;
/*  10:    */ import com.asinfo.as2.polizas.configuracion.servicio.ServicioTipoAsegurado;
/*  11:    */ import com.asinfo.as2.util.AppUtil;
/*  12:    */ import java.util.ArrayList;
/*  13:    */ import java.util.List;
/*  14:    */ import java.util.Map;
/*  15:    */ import javax.annotation.PostConstruct;
/*  16:    */ import javax.ejb.EJB;
/*  17:    */ import javax.faces.bean.ManagedBean;
/*  18:    */ import javax.faces.bean.ViewScoped;
/*  19:    */ import org.apache.log4j.Logger;
/*  20:    */ import org.primefaces.component.datatable.DataTable;
/*  21:    */ import org.primefaces.model.LazyDataModel;
/*  22:    */ import org.primefaces.model.SortOrder;
/*  23:    */ 
/*  24:    */ @ManagedBean
/*  25:    */ @ViewScoped
/*  26:    */ public class AseguradoBean
/*  27:    */   extends PageControllerAS2
/*  28:    */ {
/*  29:    */   private static final long serialVersionUID = 1L;
/*  30:    */   @EJB
/*  31:    */   private ServicioAsegurado servicioAsegurado;
/*  32:    */   @EJB
/*  33:    */   private ServicioTipoAsegurado servicioTipoAsegurado;
/*  34:    */   private Asegurado asegurado;
/*  35:    */   private LazyDataModel<Asegurado> listaAsegurado;
/*  36:    */   private List<TipoAsegurado> listaTipoAsegurado;
/*  37:    */   private DataTable dtAsegurado;
/*  38:    */   
/*  39:    */   @PostConstruct
/*  40:    */   public void init()
/*  41:    */   {
/*  42: 76 */     this.listaAsegurado = new LazyDataModel()
/*  43:    */     {
/*  44:    */       private static final long serialVersionUID = 1L;
/*  45:    */       
/*  46:    */       public List<Asegurado> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  47:    */       {
/*  48: 83 */         List<Asegurado> lista = new ArrayList();
/*  49: 84 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  50:    */         
/*  51: 86 */         lista = AseguradoBean.this.servicioAsegurado.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  52:    */         
/*  53: 88 */         AseguradoBean.this.listaAsegurado.setRowCount(AseguradoBean.this.servicioAsegurado.contarPorCriterio(filters));
/*  54: 89 */         return lista;
/*  55:    */       }
/*  56:    */     };
/*  57:    */   }
/*  58:    */   
/*  59:    */   private void crearAsegurado()
/*  60:    */   {
/*  61:103 */     this.asegurado = new Asegurado();
/*  62:104 */     this.asegurado.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  63:105 */     this.asegurado.setIdSucursal(AppUtil.getSucursal().getId());
/*  64:106 */     this.asegurado.setTipoAsegurado(new TipoAsegurado());
/*  65:    */   }
/*  66:    */   
/*  67:    */   public String editar()
/*  68:    */   {
/*  69:115 */     if (getAsegurado().getIdAsegurado() > 0) {
/*  70:116 */       setEditado(true);
/*  71:    */     } else {
/*  72:118 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  73:    */     }
/*  74:120 */     return "";
/*  75:    */   }
/*  76:    */   
/*  77:    */   public String guardar()
/*  78:    */   {
/*  79:    */     try
/*  80:    */     {
/*  81:130 */       this.servicioAsegurado.guardar(getAsegurado());
/*  82:131 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  83:132 */       setEditado(false);
/*  84:133 */       limpiar();
/*  85:    */     }
/*  86:    */     catch (Exception e)
/*  87:    */     {
/*  88:135 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  89:136 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  90:    */     }
/*  91:138 */     return "";
/*  92:    */   }
/*  93:    */   
/*  94:    */   public String eliminar()
/*  95:    */   {
/*  96:    */     try
/*  97:    */     {
/*  98:148 */       this.servicioAsegurado.eliminar(getAsegurado());
/*  99:149 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 100:    */     }
/* 101:    */     catch (Exception e)
/* 102:    */     {
/* 103:151 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 104:152 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 105:    */     }
/* 106:154 */     return "";
/* 107:    */   }
/* 108:    */   
/* 109:    */   public String cargarDatos()
/* 110:    */   {
/* 111:163 */     return "";
/* 112:    */   }
/* 113:    */   
/* 114:    */   public String limpiar()
/* 115:    */   {
/* 116:172 */     crearAsegurado();
/* 117:173 */     return "";
/* 118:    */   }
/* 119:    */   
/* 120:    */   public Asegurado getAsegurado()
/* 121:    */   {
/* 122:192 */     if (this.asegurado == null) {
/* 123:193 */       crearAsegurado();
/* 124:    */     }
/* 125:195 */     return this.asegurado;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void setAsegurado(Asegurado asegurado)
/* 129:    */   {
/* 130:206 */     this.asegurado = asegurado;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public LazyDataModel<Asegurado> getListaAsegurado()
/* 134:    */   {
/* 135:215 */     return this.listaAsegurado;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void setListaAsegurado(LazyDataModel<Asegurado> listaAsegurado)
/* 139:    */   {
/* 140:225 */     this.listaAsegurado = listaAsegurado;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public List<TipoAsegurado> getListaTipoAsegurado()
/* 144:    */   {
/* 145:234 */     if (this.listaTipoAsegurado == null) {
/* 146:235 */       this.listaTipoAsegurado = this.servicioTipoAsegurado.obtenerListaCombo("nombre", true, null);
/* 147:    */     }
/* 148:237 */     return this.listaTipoAsegurado;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void setListaTipoAsegurado(List<TipoAsegurado> listaTipoAsegurado)
/* 152:    */   {
/* 153:247 */     this.listaTipoAsegurado = listaTipoAsegurado;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public DataTable getDtAsegurado()
/* 157:    */   {
/* 158:256 */     return this.dtAsegurado;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void setDtAsegurado(DataTable dtAsegurado)
/* 162:    */   {
/* 163:266 */     this.dtAsegurado = dtAsegurado;
/* 164:    */   }
/* 165:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.polizas.configuracion.AseguradoBean
 * JD-Core Version:    0.7.0.1
 */