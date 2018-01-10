/*   1:    */ package com.asinfo.as2.compras.importaciones.configuracion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compras.importaciones.configuracion.servicio.ServicioProcesoImportacion;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.entities.ProcesoImportacion;
/*   8:    */ import com.asinfo.as2.entities.Sucursal;
/*   9:    */ import com.asinfo.as2.util.AppUtil;
/*  10:    */ import java.util.ArrayList;
/*  11:    */ import java.util.List;
/*  12:    */ import java.util.Map;
/*  13:    */ import javax.annotation.PostConstruct;
/*  14:    */ import javax.ejb.EJB;
/*  15:    */ import javax.faces.bean.ManagedBean;
/*  16:    */ import javax.faces.bean.ViewScoped;
/*  17:    */ import javax.faces.model.SelectItem;
/*  18:    */ import org.apache.log4j.Logger;
/*  19:    */ import org.primefaces.component.datatable.DataTable;
/*  20:    */ import org.primefaces.event.SelectEvent;
/*  21:    */ import org.primefaces.model.LazyDataModel;
/*  22:    */ import org.primefaces.model.SortOrder;
/*  23:    */ 
/*  24:    */ @ManagedBean
/*  25:    */ @ViewScoped
/*  26:    */ public class ProcesoImportacionBean
/*  27:    */   extends PageControllerAS2
/*  28:    */ {
/*  29:    */   private static final long serialVersionUID = 1L;
/*  30:    */   @EJB
/*  31:    */   private ServicioProcesoImportacion servicioProcesoImportacion;
/*  32:    */   private ProcesoImportacion procesoImportacion;
/*  33:    */   private LazyDataModel<ProcesoImportacion> listaProcesoImportacion;
/*  34:    */   private List<SelectItem> bancoItems;
/*  35:    */   private DataTable dtProcesoImportacion;
/*  36:    */   
/*  37:    */   @PostConstruct
/*  38:    */   public void init()
/*  39:    */   {
/*  40: 61 */     this.listaProcesoImportacion = new LazyDataModel()
/*  41:    */     {
/*  42:    */       private static final long serialVersionUID = 1L;
/*  43:    */       
/*  44:    */       public List<ProcesoImportacion> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  45:    */       {
/*  46: 68 */         List<ProcesoImportacion> lista = new ArrayList();
/*  47: 69 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  48:    */         
/*  49: 71 */         lista = ProcesoImportacionBean.this.servicioProcesoImportacion.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  50: 72 */         ProcesoImportacionBean.this.listaProcesoImportacion.setRowCount(ProcesoImportacionBean.this.servicioProcesoImportacion.contarPorCriterio(filters));
/*  51:    */         
/*  52: 74 */         return lista;
/*  53:    */       }
/*  54:    */     };
/*  55:    */   }
/*  56:    */   
/*  57:    */   public String editar()
/*  58:    */   {
/*  59: 87 */     if (getProcesoImportacion().getId() > 0) {
/*  60: 88 */       setEditado(true);
/*  61:    */     } else {
/*  62: 90 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  63:    */     }
/*  64: 92 */     return "";
/*  65:    */   }
/*  66:    */   
/*  67:    */   public String guardar()
/*  68:    */   {
/*  69:    */     try
/*  70:    */     {
/*  71:103 */       this.servicioProcesoImportacion.guardar(this.procesoImportacion);
/*  72:104 */       limpiar();
/*  73:105 */       setEditado(false);
/*  74:106 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  75:    */     }
/*  76:    */     catch (Exception e)
/*  77:    */     {
/*  78:108 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  79:109 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  80:    */     }
/*  81:111 */     return "";
/*  82:    */   }
/*  83:    */   
/*  84:    */   public String limpiar()
/*  85:    */   {
/*  86:121 */     crearProcesoImportacion();
/*  87:122 */     return "";
/*  88:    */   }
/*  89:    */   
/*  90:    */   public String eliminar()
/*  91:    */   {
/*  92:    */     try
/*  93:    */     {
/*  94:133 */       this.servicioProcesoImportacion.eliminar(this.procesoImportacion);
/*  95:134 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/*  96:    */     }
/*  97:    */     catch (Exception e)
/*  98:    */     {
/*  99:136 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 100:137 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 101:    */     }
/* 102:139 */     return "";
/* 103:    */   }
/* 104:    */   
/* 105:    */   public String cargarDatos()
/* 106:    */   {
/* 107:149 */     return "";
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void cargarDatosProcesoImportacion()
/* 111:    */   {
/* 112:167 */     List<ProcesoImportacion> bancos = new ArrayList();
/* 113:168 */     bancos = getServicioProcesoImportacionBean().obtenerListaCombo("nombre", true, null);
/* 114:169 */     this.bancoItems = new ArrayList();
/* 115:171 */     for (ProcesoImportacion bancoX : bancos)
/* 116:    */     {
/* 117:172 */       int value = bancoX.getIdProcesoImportacion();
/* 118:173 */       String label = bancoX.getNombre();
/* 119:174 */       SelectItem opcion = new SelectItem(Integer.valueOf(value), label);
/* 120:175 */       this.bancoItems.add(opcion);
/* 121:    */     }
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void crearProcesoImportacion()
/* 125:    */   {
/* 126:185 */     this.procesoImportacion = new ProcesoImportacion();
/* 127:186 */     this.procesoImportacion.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 128:187 */     this.procesoImportacion.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void onRowSelect(SelectEvent event)
/* 132:    */   {
/* 133:194 */     ProcesoImportacion banco1 = (ProcesoImportacion)event.getObject();
/* 134:195 */     setProcesoImportacion(banco1);
/* 135:    */   }
/* 136:    */   
/* 137:    */   public ServicioProcesoImportacion getServicioProcesoImportacionBean()
/* 138:    */   {
/* 139:204 */     return this.servicioProcesoImportacion;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void setServicioProcesoImportacionBean(ServicioProcesoImportacion servicioProcesoImportacionBean)
/* 143:    */   {
/* 144:214 */     this.servicioProcesoImportacion = servicioProcesoImportacionBean;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public ProcesoImportacion getProcesoImportacion()
/* 148:    */   {
/* 149:223 */     if (this.procesoImportacion == null) {
/* 150:224 */       crearProcesoImportacion();
/* 151:    */     }
/* 152:226 */     return this.procesoImportacion;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void setProcesoImportacion(ProcesoImportacion banco)
/* 156:    */   {
/* 157:236 */     this.procesoImportacion = banco;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public LazyDataModel<ProcesoImportacion> getListaProcesoImportacion()
/* 161:    */   {
/* 162:245 */     return this.listaProcesoImportacion;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void setListaProcesoImportacion(LazyDataModel<ProcesoImportacion> listaProcesoImportacion)
/* 166:    */   {
/* 167:255 */     this.listaProcesoImportacion = listaProcesoImportacion;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public List<SelectItem> getProcesoImportacionItems()
/* 171:    */   {
/* 172:264 */     return this.bancoItems;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void setProcesoImportacionItems(List<SelectItem> bancoItems)
/* 176:    */   {
/* 177:274 */     this.bancoItems = bancoItems;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public DataTable getDtProcesoImportacion()
/* 181:    */   {
/* 182:283 */     return this.dtProcesoImportacion;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public void setDtProcesoImportacion(DataTable dtProcesoImportacion)
/* 186:    */   {
/* 187:293 */     this.dtProcesoImportacion = dtProcesoImportacion;
/* 188:    */   }
/* 189:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.importaciones.configuracion.ProcesoImportacionBean
 * JD-Core Version:    0.7.0.1
 */