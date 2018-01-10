/*   1:    */ package com.asinfo.as2.ventas.configuracion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.entities.Sucursal;
/*   7:    */ import com.asinfo.as2.entities.TipoOrdenDespacho;
/*   8:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*   9:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  10:    */ import com.asinfo.as2.util.AppUtil;
/*  11:    */ import com.asinfo.as2.utils.JsfUtil;
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
/*  26:    */ public class TipoOrdenDespachoBean
/*  27:    */   extends PageControllerAS2
/*  28:    */ {
/*  29:    */   private static final long serialVersionUID = 1L;
/*  30:    */   @EJB
/*  31:    */   private ServicioGenerico<TipoOrdenDespacho> servicioTipoOrdenDespacho;
/*  32:    */   private TipoOrdenDespacho tipoOrdenDespacho;
/*  33:    */   private LazyDataModel<TipoOrdenDespacho> listaTipoOrdenDespacho;
/*  34:    */   private DataTable dataTableTipoOrdenDespacho;
/*  35:    */   
/*  36:    */   @PostConstruct
/*  37:    */   public void init()
/*  38:    */   {
/*  39: 69 */     this.listaTipoOrdenDespacho = new LazyDataModel()
/*  40:    */     {
/*  41:    */       private static final long serialVersionUID = -1956844755215090557L;
/*  42:    */       
/*  43:    */       public List<TipoOrdenDespacho> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  44:    */       {
/*  45: 75 */         List<TipoOrdenDespacho> lista = new ArrayList();
/*  46: 76 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  47:    */         try
/*  48:    */         {
/*  49: 78 */           lista = TipoOrdenDespachoBean.this.servicioTipoOrdenDespacho.obtenerListaPorPagina(TipoOrdenDespacho.class, startIndex, pageSize, sortField, ordenar, filters);
/*  50:    */         }
/*  51:    */         catch (Exception e)
/*  52:    */         {
/*  53: 82 */           e.printStackTrace();
/*  54:    */         }
/*  55: 84 */         TipoOrdenDespachoBean.this.listaTipoOrdenDespacho.setRowCount(TipoOrdenDespachoBean.this.servicioTipoOrdenDespacho.contarPorCriterio(TipoOrdenDespacho.class, filters));
/*  56: 85 */         return lista;
/*  57:    */       }
/*  58:    */     };
/*  59:    */   }
/*  60:    */   
/*  61:    */   public String editar()
/*  62:    */   {
/*  63: 98 */     if (getTipoOrdenDespacho().getIdTipoOrdenDespacho() > 0) {
/*  64: 99 */       setEditado(true);
/*  65:    */     } else {
/*  66:101 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  67:    */     }
/*  68:104 */     return "";
/*  69:    */   }
/*  70:    */   
/*  71:    */   public String guardar()
/*  72:    */   {
/*  73:    */     try
/*  74:    */     {
/*  75:115 */       this.servicioTipoOrdenDespacho.guardarValidar(this.tipoOrdenDespacho);
/*  76:116 */       cargarDatos();
/*  77:    */       
/*  78:118 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  79:    */     }
/*  80:    */     catch (AS2Exception e)
/*  81:    */     {
/*  82:120 */       JsfUtil.addErrorMessage(e, "");
/*  83:    */     }
/*  84:    */     catch (Exception e)
/*  85:    */     {
/*  86:122 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  87:123 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  88:    */     }
/*  89:125 */     return "";
/*  90:    */   }
/*  91:    */   
/*  92:    */   public String eliminar()
/*  93:    */   {
/*  94:    */     try
/*  95:    */     {
/*  96:136 */       this.servicioTipoOrdenDespacho.eliminar(this.tipoOrdenDespacho);
/*  97:137 */       cargarDatos();
/*  98:    */       
/*  99:139 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 100:    */     }
/* 101:    */     catch (Exception e)
/* 102:    */     {
/* 103:141 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 104:142 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 105:    */     }
/* 106:144 */     return "";
/* 107:    */   }
/* 108:    */   
/* 109:    */   public String cargarDatos()
/* 110:    */   {
/* 111:155 */     setEditado(false);
/* 112:    */     try
/* 113:    */     {
/* 114:158 */       limpiar();
/* 115:    */     }
/* 116:    */     catch (Exception e)
/* 117:    */     {
/* 118:161 */       LOG.error("ERROR AL CARGAR DATOS", e);
/* 119:162 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 120:    */     }
/* 121:164 */     return "";
/* 122:    */   }
/* 123:    */   
/* 124:    */   public String limpiar()
/* 125:    */   {
/* 126:174 */     this.tipoOrdenDespacho = new TipoOrdenDespacho();
/* 127:175 */     this.tipoOrdenDespacho.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 128:176 */     this.tipoOrdenDespacho.setIdSucursal(AppUtil.getSucursal().getId());
/* 129:177 */     return "";
/* 130:    */   }
/* 131:    */   
/* 132:    */   public TipoOrdenDespacho getTipoOrdenDespacho()
/* 133:    */   {
/* 134:186 */     return this.tipoOrdenDespacho;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void setTipoOrdenDespacho(TipoOrdenDespacho tipoOrdenDespacho)
/* 138:    */   {
/* 139:196 */     this.tipoOrdenDespacho = tipoOrdenDespacho;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public DataTable getDataTableTipoOrdenDespacho()
/* 143:    */   {
/* 144:205 */     return this.dataTableTipoOrdenDespacho;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void setDataTableTipoOrdenDespacho(DataTable dataTableTipoOrdenDespacho)
/* 148:    */   {
/* 149:209 */     this.dataTableTipoOrdenDespacho = dataTableTipoOrdenDespacho;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public LazyDataModel<TipoOrdenDespacho> getListaTipoOrdenDespacho()
/* 153:    */   {
/* 154:213 */     if (this.listaTipoOrdenDespacho == null) {
/* 155:214 */       cargarDatos();
/* 156:    */     }
/* 157:216 */     return this.listaTipoOrdenDespacho;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void setListaTipoOrdenDespacho(LazyDataModel<TipoOrdenDespacho> listaTipoOrdenDespacho)
/* 161:    */   {
/* 162:220 */     this.listaTipoOrdenDespacho = listaTipoOrdenDespacho;
/* 163:    */   }
/* 164:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.configuracion.TipoOrdenDespachoBean
 * JD-Core Version:    0.7.0.1
 */