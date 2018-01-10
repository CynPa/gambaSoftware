/*   1:    */ package com.asinfo.as2.mantenimiento.configuracion.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.entities.Sucursal;
/*   7:    */ import com.asinfo.as2.entities.mantenimiento.Frecuencia;
/*   8:    */ import com.asinfo.as2.enumeraciones.FrecuenciaFechaEnum;
/*   9:    */ import com.asinfo.as2.enumeraciones.TipoFrecuenciaEnum;
/*  10:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  11:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  12:    */ import com.asinfo.as2.util.AppUtil;
/*  13:    */ import com.asinfo.as2.utils.JsfUtil;
/*  14:    */ import com.asinfo.as2.utils.comparator.SelectItemComparator;
/*  15:    */ import java.util.ArrayList;
/*  16:    */ import java.util.Arrays;
/*  17:    */ import java.util.List;
/*  18:    */ import java.util.Map;
/*  19:    */ import javax.annotation.PostConstruct;
/*  20:    */ import javax.ejb.EJB;
/*  21:    */ import javax.faces.bean.ManagedBean;
/*  22:    */ import javax.faces.bean.ViewScoped;
/*  23:    */ import javax.faces.model.SelectItem;
/*  24:    */ import org.apache.log4j.Logger;
/*  25:    */ import org.primefaces.component.datatable.DataTable;
/*  26:    */ import org.primefaces.model.LazyDataModel;
/*  27:    */ import org.primefaces.model.SortOrder;
/*  28:    */ 
/*  29:    */ @ManagedBean
/*  30:    */ @ViewScoped
/*  31:    */ public class FrecuenciaBean
/*  32:    */   extends PageControllerAS2
/*  33:    */ {
/*  34:    */   private static final long serialVersionUID = 1L;
/*  35:    */   @EJB
/*  36:    */   private ServicioGenerico<Frecuencia> servicioFrecuencia;
/*  37:    */   private Frecuencia frecuencia;
/*  38:    */   private LazyDataModel<Frecuencia> listaFrecuencia;
/*  39:    */   private List<SelectItem> listaTipoFrecuencia;
/*  40:    */   private List<SelectItem> listaFrecuenciaFecha;
/*  41:    */   private SelectItem[] listaTipoFrecuenciaItem;
/*  42:    */   private SelectItem[] listaFrecuenciaFechaItem;
/*  43:    */   private DataTable dtFrecuencia;
/*  44:    */   
/*  45:    */   @PostConstruct
/*  46:    */   public void init()
/*  47:    */   {
/*  48: 62 */     this.listaFrecuencia = new LazyDataModel()
/*  49:    */     {
/*  50:    */       private static final long serialVersionUID = 1L;
/*  51:    */       
/*  52:    */       public List<Frecuencia> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  53:    */       {
/*  54: 69 */         List<Frecuencia> lista = new ArrayList();
/*  55: 70 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  56:    */         
/*  57: 72 */         List<String> lcampos = new ArrayList();
/*  58:    */         
/*  59: 74 */         lista = FrecuenciaBean.this.servicioFrecuencia.obtenerListaPorPagina(Frecuencia.class, startIndex, pageSize, sortField, ordenar, filters, lcampos);
/*  60: 75 */         FrecuenciaBean.this.listaFrecuencia.setRowCount(FrecuenciaBean.this.servicioFrecuencia.contarPorCriterio(Frecuencia.class, filters));
/*  61:    */         
/*  62: 77 */         return lista;
/*  63:    */       }
/*  64:    */     };
/*  65:    */   }
/*  66:    */   
/*  67:    */   public String editar()
/*  68:    */   {
/*  69: 90 */     if ((getFrecuencia() != null) && (getFrecuencia().getId() != 0)) {
/*  70: 91 */       setEditado(true);
/*  71:    */     } else {
/*  72: 93 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  73:    */     }
/*  74: 95 */     return "";
/*  75:    */   }
/*  76:    */   
/*  77:    */   public String guardar()
/*  78:    */   {
/*  79:    */     try
/*  80:    */     {
/*  81:106 */       this.servicioFrecuencia.guardarValidar(this.frecuencia);
/*  82:107 */       limpiar();
/*  83:108 */       setEditado(false);
/*  84:109 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  85:    */     }
/*  86:    */     catch (AS2Exception e)
/*  87:    */     {
/*  88:111 */       JsfUtil.addErrorMessage(e, "");
/*  89:    */     }
/*  90:    */     catch (Exception e)
/*  91:    */     {
/*  92:113 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  93:114 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  94:    */     }
/*  95:116 */     return "";
/*  96:    */   }
/*  97:    */   
/*  98:    */   public String limpiar()
/*  99:    */   {
/* 100:126 */     crearFrecuencia();
/* 101:127 */     return "";
/* 102:    */   }
/* 103:    */   
/* 104:    */   public String eliminar()
/* 105:    */   {
/* 106:137 */     if ((getFrecuencia() != null) && (getFrecuencia().getId() != 0)) {
/* 107:    */       try
/* 108:    */       {
/* 109:139 */         this.servicioFrecuencia.eliminar(this.frecuencia);
/* 110:140 */         addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 111:    */       }
/* 112:    */       catch (Exception e)
/* 113:    */       {
/* 114:142 */         addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 115:143 */         LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 116:    */       }
/* 117:    */     } else {
/* 118:146 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 119:    */     }
/* 120:149 */     return "";
/* 121:    */   }
/* 122:    */   
/* 123:    */   public String cargarDatos()
/* 124:    */   {
/* 125:159 */     return "";
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void crearFrecuencia()
/* 129:    */   {
/* 130:166 */     this.frecuencia = new Frecuencia();
/* 131:167 */     this.frecuencia.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 132:168 */     this.frecuencia.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 133:169 */     this.frecuencia.setActivo(true);
/* 134:    */   }
/* 135:    */   
/* 136:    */   public Frecuencia getFrecuencia()
/* 137:    */   {
/* 138:178 */     if (this.frecuencia == null) {
/* 139:179 */       crearFrecuencia();
/* 140:    */     }
/* 141:181 */     return this.frecuencia;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public void setFrecuencia(Frecuencia Frecuencia)
/* 145:    */   {
/* 146:191 */     this.frecuencia = Frecuencia;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public LazyDataModel<Frecuencia> getListaFrecuencia()
/* 150:    */   {
/* 151:200 */     return this.listaFrecuencia;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public void setListaFrecuencia(LazyDataModel<Frecuencia> listaFrecuencia)
/* 155:    */   {
/* 156:210 */     this.listaFrecuencia = listaFrecuencia;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public DataTable getDtFrecuencia()
/* 160:    */   {
/* 161:219 */     return this.dtFrecuencia;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public void setDtFrecuencia(DataTable dtFrecuencia)
/* 165:    */   {
/* 166:229 */     this.dtFrecuencia = dtFrecuencia;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public List<SelectItem> getListaTipoFrecuencia()
/* 170:    */   {
/* 171:236 */     if (this.listaTipoFrecuencia == null)
/* 172:    */     {
/* 173:237 */       this.listaTipoFrecuencia = new ArrayList();
/* 174:238 */       for (TipoFrecuenciaEnum tipo : TipoFrecuenciaEnum.values()) {
/* 175:239 */         this.listaTipoFrecuencia.add(new SelectItem(tipo, tipo.getNombre()));
/* 176:    */       }
/* 177:    */     }
/* 178:243 */     return this.listaTipoFrecuencia;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public List<SelectItem> getListaFrecuenciaFecha()
/* 182:    */   {
/* 183:247 */     if (this.listaFrecuenciaFecha == null)
/* 184:    */     {
/* 185:248 */       this.listaFrecuenciaFecha = new ArrayList();
/* 186:249 */       for (FrecuenciaFechaEnum enumerador : FrecuenciaFechaEnum.values()) {
/* 187:250 */         this.listaFrecuenciaFecha.add(new SelectItem(enumerador, enumerador.getNombre()));
/* 188:    */       }
/* 189:    */     }
/* 190:254 */     return this.listaFrecuenciaFecha;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public SelectItem[] getListaTipoFrecuenciaItem()
/* 194:    */   {
/* 195:263 */     if (this.listaTipoFrecuenciaItem == null)
/* 196:    */     {
/* 197:265 */       List<SelectItem> lista = new ArrayList();
/* 198:266 */       lista.add(new SelectItem("", ""));
/* 199:268 */       for (TipoFrecuenciaEnum t : TipoFrecuenciaEnum.values())
/* 200:    */       {
/* 201:269 */         SelectItem item = new SelectItem(t, t.getNombre());
/* 202:270 */         lista.add(item);
/* 203:    */       }
/* 204:272 */       this.listaTipoFrecuenciaItem = ((SelectItem[])lista.toArray(new SelectItem[lista.size()]));
/* 205:    */     }
/* 206:275 */     Arrays.sort(this.listaTipoFrecuenciaItem, new SelectItemComparator());
/* 207:    */     
/* 208:277 */     return this.listaTipoFrecuenciaItem;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public SelectItem[] getListaFrecuenciaFechaItem()
/* 212:    */   {
/* 213:286 */     if (this.listaFrecuenciaFechaItem == null)
/* 214:    */     {
/* 215:288 */       List<SelectItem> lista = new ArrayList();
/* 216:289 */       lista.add(new SelectItem("", ""));
/* 217:291 */       for (FrecuenciaFechaEnum t : FrecuenciaFechaEnum.values())
/* 218:    */       {
/* 219:292 */         SelectItem item = new SelectItem(t, t.getNombre());
/* 220:293 */         lista.add(item);
/* 221:    */       }
/* 222:295 */       this.listaFrecuenciaFechaItem = ((SelectItem[])lista.toArray(new SelectItem[lista.size()]));
/* 223:    */     }
/* 224:298 */     Arrays.sort(this.listaFrecuenciaFechaItem, new SelectItemComparator());
/* 225:    */     
/* 226:300 */     return this.listaFrecuenciaFechaItem;
/* 227:    */   }
/* 228:    */   
/* 229:    */   public void actualizarTipoFrecuencia()
/* 230:    */   {
/* 231:304 */     this.frecuencia.setFrecuenciaFechaEnum(null);
/* 232:305 */     this.frecuencia.setIndicadorAcumulativo(false);
/* 233:    */   }
/* 234:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.mantenimiento.configuracion.controller.FrecuenciaBean
 * JD-Core Version:    0.7.0.1
 */