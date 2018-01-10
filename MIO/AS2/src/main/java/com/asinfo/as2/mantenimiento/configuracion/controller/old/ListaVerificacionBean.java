/*   1:    */ package com.asinfo.as2.mantenimiento.configuracion.controller.old;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.entities.Sucursal;
/*   7:    */ import com.asinfo.as2.entities.mantenimiento.old.DetalleListaVerificacion;
/*   8:    */ import com.asinfo.as2.entities.mantenimiento.old.ListaVerificacion;
/*   9:    */ import com.asinfo.as2.enumeraciones.TipoAtributo;
/*  10:    */ import com.asinfo.as2.mantenimiento.configuracion.old.ServicioListaVerificacion;
/*  11:    */ import com.asinfo.as2.util.AppUtil;
/*  12:    */ import java.util.ArrayList;
/*  13:    */ import java.util.List;
/*  14:    */ import java.util.Map;
/*  15:    */ import javax.annotation.PostConstruct;
/*  16:    */ import javax.ejb.EJB;
/*  17:    */ import javax.faces.bean.ManagedBean;
/*  18:    */ import javax.faces.bean.ViewScoped;
/*  19:    */ import javax.faces.model.SelectItem;
/*  20:    */ import org.apache.log4j.Logger;
/*  21:    */ import org.primefaces.component.datatable.DataTable;
/*  22:    */ import org.primefaces.model.LazyDataModel;
/*  23:    */ import org.primefaces.model.SortOrder;
/*  24:    */ 
/*  25:    */ @ManagedBean
/*  26:    */ @ViewScoped
/*  27:    */ public class ListaVerificacionBean
/*  28:    */   extends PageControllerAS2
/*  29:    */ {
/*  30:    */   private static final long serialVersionUID = -2518505713762501239L;
/*  31:    */   @EJB
/*  32:    */   private ServicioListaVerificacion servicioListaVerificacion;
/*  33:    */   private ListaVerificacion listaVerificacion;
/*  34:    */   private List<SelectItem> listaTipoAtributo;
/*  35:    */   private LazyDataModel<ListaVerificacion> listaListaVerificacion;
/*  36:    */   private DataTable dtListaVerificacion;
/*  37:    */   private DataTable dtDetalleListaVerificacion;
/*  38:    */   
/*  39:    */   @PostConstruct
/*  40:    */   public void init()
/*  41:    */   {
/*  42: 82 */     this.listaListaVerificacion = new LazyDataModel()
/*  43:    */     {
/*  44:    */       private static final long serialVersionUID = 1L;
/*  45:    */       
/*  46:    */       public List<ListaVerificacion> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  47:    */       {
/*  48: 89 */         List<ListaVerificacion> lista = new ArrayList();
/*  49: 90 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  50:    */         
/*  51: 92 */         filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/*  52: 93 */         lista = ListaVerificacionBean.this.servicioListaVerificacion.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  53:    */         
/*  54: 95 */         ListaVerificacionBean.this.listaListaVerificacion.setRowCount(ListaVerificacionBean.this.servicioListaVerificacion.contarPorCriterio(filters));
/*  55:    */         
/*  56: 97 */         return lista;
/*  57:    */       }
/*  58:    */     };
/*  59:    */   }
/*  60:    */   
/*  61:    */   private void crearEntidad()
/*  62:    */   {
/*  63:115 */     this.listaVerificacion = new ListaVerificacion();
/*  64:116 */     this.listaVerificacion.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/*  65:117 */     this.listaVerificacion.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/*  66:    */   }
/*  67:    */   
/*  68:    */   public String editar()
/*  69:    */   {
/*  70:126 */     if (getListaVerificacion().getIdListaVerificacion() > 0)
/*  71:    */     {
/*  72:127 */       this.listaVerificacion = this.servicioListaVerificacion.cargarDetalle(getListaVerificacion().getIdListaVerificacion());
/*  73:128 */       setEditado(true);
/*  74:    */     }
/*  75:    */     else
/*  76:    */     {
/*  77:130 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  78:    */     }
/*  79:132 */     return "";
/*  80:    */   }
/*  81:    */   
/*  82:    */   public String guardar()
/*  83:    */   {
/*  84:    */     try
/*  85:    */     {
/*  86:142 */       this.servicioListaVerificacion.guardar(this.listaVerificacion);
/*  87:143 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  88:144 */       limpiar();
/*  89:145 */       setEditado(false);
/*  90:    */     }
/*  91:    */     catch (Exception e)
/*  92:    */     {
/*  93:147 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  94:148 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  95:    */     }
/*  96:150 */     return "";
/*  97:    */   }
/*  98:    */   
/*  99:    */   public String eliminar()
/* 100:    */   {
/* 101:    */     try
/* 102:    */     {
/* 103:160 */       this.servicioListaVerificacion.eliminar(this.listaVerificacion);
/* 104:161 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 105:    */     }
/* 106:    */     catch (Exception e)
/* 107:    */     {
/* 108:163 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 109:164 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 110:    */     }
/* 111:166 */     return "";
/* 112:    */   }
/* 113:    */   
/* 114:    */   public String cargarDatos()
/* 115:    */   {
/* 116:175 */     return "";
/* 117:    */   }
/* 118:    */   
/* 119:    */   public String limpiar()
/* 120:    */   {
/* 121:184 */     crearEntidad();
/* 122:185 */     return "";
/* 123:    */   }
/* 124:    */   
/* 125:    */   public String agregarDetalle()
/* 126:    */   {
/* 127:189 */     DetalleListaVerificacion detalleListaVerificacion = new DetalleListaVerificacion();
/* 128:190 */     detalleListaVerificacion.setListaVerificacion(this.listaVerificacion);
/* 129:191 */     getListaVerificacion().getListaDetalleListaVerificacion().add(detalleListaVerificacion);
/* 130:192 */     return "";
/* 131:    */   }
/* 132:    */   
/* 133:    */   public String eliminarDetalle()
/* 134:    */   {
/* 135:196 */     DetalleListaVerificacion d = (DetalleListaVerificacion)this.dtDetalleListaVerificacion.getRowData();
/* 136:197 */     d.setEliminado(true);
/* 137:198 */     return "";
/* 138:    */   }
/* 139:    */   
/* 140:    */   public ListaVerificacion getListaVerificacion()
/* 141:    */   {
/* 142:212 */     return this.listaVerificacion;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void setListaVerificacion(ListaVerificacion listaVerificacion)
/* 146:    */   {
/* 147:222 */     this.listaVerificacion = listaVerificacion;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public LazyDataModel<ListaVerificacion> getListaListaVerificacion()
/* 151:    */   {
/* 152:231 */     return this.listaListaVerificacion;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void setListaListaVerificacion(LazyDataModel<ListaVerificacion> listaListaVerificacion)
/* 156:    */   {
/* 157:241 */     this.listaListaVerificacion = listaListaVerificacion;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public DataTable getDtListaVerificacion()
/* 161:    */   {
/* 162:250 */     return this.dtListaVerificacion;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void setDtListaVerificacion(DataTable dtListaVerificacion)
/* 166:    */   {
/* 167:260 */     this.dtListaVerificacion = dtListaVerificacion;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public List<SelectItem> getListaTipoAtributo()
/* 171:    */   {
/* 172:269 */     if (this.listaTipoAtributo == null)
/* 173:    */     {
/* 174:270 */       this.listaTipoAtributo = new ArrayList();
/* 175:272 */       for (TipoAtributo t : TipoAtributo.values())
/* 176:    */       {
/* 177:273 */         SelectItem item = new SelectItem(t, t.getNombre());
/* 178:274 */         this.listaTipoAtributo.add(item);
/* 179:    */       }
/* 180:    */     }
/* 181:277 */     return this.listaTipoAtributo;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public void setListaTipoAtributo(List<SelectItem> listaTipoAtributo)
/* 185:    */   {
/* 186:287 */     this.listaTipoAtributo = listaTipoAtributo;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public List<DetalleListaVerificacion> getDetalleListaVerificacion()
/* 190:    */   {
/* 191:296 */     List<DetalleListaVerificacion> lista = new ArrayList();
/* 192:297 */     for (DetalleListaVerificacion dlv : getListaVerificacion().getListaDetalleListaVerificacion()) {
/* 193:298 */       if (!dlv.isEliminado()) {
/* 194:299 */         lista.add(dlv);
/* 195:    */       }
/* 196:    */     }
/* 197:302 */     return lista;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public DataTable getDtDetalleListaVerificacion()
/* 201:    */   {
/* 202:311 */     return this.dtDetalleListaVerificacion;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public void setDtDetalleListaVerificacion(DataTable dtDetalleListaVerificacion)
/* 206:    */   {
/* 207:321 */     this.dtDetalleListaVerificacion = dtDetalleListaVerificacion;
/* 208:    */   }
/* 209:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.mantenimiento.configuracion.controller.old.ListaVerificacionBean
 * JD-Core Version:    0.7.0.1
 */