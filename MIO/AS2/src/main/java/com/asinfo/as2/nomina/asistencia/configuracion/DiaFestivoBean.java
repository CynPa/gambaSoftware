/*   1:    */ package com.asinfo.as2.nomina.asistencia.configuracion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.entities.Sucursal;
/*   7:    */ import com.asinfo.as2.entities.nomina.asistencia.DiaFestivo;
/*   8:    */ import com.asinfo.as2.enumeraciones.Mes;
/*   9:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  10:    */ import com.asinfo.as2.util.AppUtil;
/*  11:    */ import java.util.ArrayList;
/*  12:    */ import java.util.Date;
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
/*  27:    */ public class DiaFestivoBean
/*  28:    */   extends PageControllerAS2
/*  29:    */ {
/*  30:    */   private static final long serialVersionUID = -6642782572366933215L;
/*  31:    */   @EJB
/*  32:    */   private ServicioGenerico<DiaFestivo> servicioDiaFestivo;
/*  33:    */   @EJB
/*  34:    */   private ServicioAsistenciaConfiguracion servicioAsistenciaConfiguracion;
/*  35:    */   private DiaFestivo diaFestivo;
/*  36:    */   private Integer annoOrigen;
/*  37:    */   private Integer annoDestino;
/*  38:    */   private LazyDataModel<DiaFestivo> listaDiaFestivo;
/*  39:    */   private DataTable dtDiaFestivo;
/*  40:    */   
/*  41:    */   @PostConstruct
/*  42:    */   public void init()
/*  43:    */   {
/*  44: 80 */     this.listaDiaFestivo = new LazyDataModel()
/*  45:    */     {
/*  46:    */       private static final long serialVersionUID = -1752987002238164010L;
/*  47:    */       
/*  48:    */       public List<DiaFestivo> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  49:    */       {
/*  50: 90 */         List<DiaFestivo> lista = new ArrayList();
/*  51: 91 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  52:    */         
/*  53: 93 */         lista = DiaFestivoBean.this.servicioDiaFestivo.obtenerListaPorPagina(DiaFestivo.class, startIndex, pageSize, sortField, ordenar, filters);
/*  54: 94 */         DiaFestivoBean.this.listaDiaFestivo.setRowCount(DiaFestivoBean.this.servicioDiaFestivo.contarPorCriterio(DiaFestivo.class, filters));
/*  55:    */         
/*  56: 96 */         return lista;
/*  57:    */       }
/*  58:    */     };
/*  59:    */   }
/*  60:    */   
/*  61:    */   public String crearDiaFestivo()
/*  62:    */   {
/*  63:109 */     this.diaFestivo = new DiaFestivo();
/*  64:110 */     this.diaFestivo.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/*  65:111 */     this.diaFestivo.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/*  66:112 */     this.diaFestivo.setFecha(new Date());
/*  67:113 */     return "";
/*  68:    */   }
/*  69:    */   
/*  70:    */   public String limpiar()
/*  71:    */   {
/*  72:122 */     crearDiaFestivo();
/*  73:123 */     return "";
/*  74:    */   }
/*  75:    */   
/*  76:    */   public String editar()
/*  77:    */   {
/*  78:132 */     if ((getDiaFestivo() != null) && (getDiaFestivo().getId() != 0)) {
/*  79:133 */       setEditado(true);
/*  80:    */     } else {
/*  81:135 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  82:    */     }
/*  83:137 */     return "";
/*  84:    */   }
/*  85:    */   
/*  86:    */   public String guardar()
/*  87:    */   {
/*  88:    */     try
/*  89:    */     {
/*  90:147 */       this.servicioDiaFestivo.guardar(getDiaFestivo());
/*  91:148 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  92:149 */       limpiar();
/*  93:150 */       setEditado(false);
/*  94:    */     }
/*  95:    */     catch (Exception e)
/*  96:    */     {
/*  97:152 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  98:153 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  99:    */     }
/* 100:155 */     return "";
/* 101:    */   }
/* 102:    */   
/* 103:    */   public String eliminar()
/* 104:    */   {
/* 105:164 */     if ((getDiaFestivo() != null) && (getDiaFestivo().getId() != 0)) {
/* 106:    */       try
/* 107:    */       {
/* 108:166 */         this.servicioDiaFestivo.eliminar(getDiaFestivo());
/* 109:167 */         addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 110:    */       }
/* 111:    */       catch (Exception e)
/* 112:    */       {
/* 113:169 */         addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 114:170 */         LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 115:    */       }
/* 116:    */     } else {
/* 117:173 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 118:    */     }
/* 119:175 */     return "";
/* 120:    */   }
/* 121:    */   
/* 122:    */   public String cargarDatos()
/* 123:    */   {
/* 124:184 */     return "";
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void copiar()
/* 128:    */   {
/* 129:188 */     this.servicioAsistenciaConfiguracion.copiarDiasFestivos(this.annoOrigen, this.annoDestino);
/* 130:189 */     this.annoDestino = null;
/* 131:190 */     this.annoOrigen = null;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public List<SelectItem> getListaMes()
/* 135:    */   {
/* 136:198 */     List<SelectItem> lista = new ArrayList();
/* 137:199 */     for (Mes item : Mes.values()) {
/* 138:200 */       lista.add(new SelectItem(item, item.getNombre()));
/* 139:    */     }
/* 140:202 */     return lista;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public List<SelectItem> getListaDiaSemana()
/* 144:    */   {
/* 145:206 */     List<SelectItem> lista = new ArrayList();
/* 146:207 */     lista.add(new SelectItem(Integer.valueOf(0), "Domingo"));
/* 147:208 */     lista.add(new SelectItem(Integer.valueOf(1), "Lunes"));
/* 148:209 */     lista.add(new SelectItem(Integer.valueOf(2), "Martes"));
/* 149:210 */     lista.add(new SelectItem(Integer.valueOf(3), "Miercoles"));
/* 150:211 */     lista.add(new SelectItem(Integer.valueOf(4), "Jueves"));
/* 151:212 */     lista.add(new SelectItem(Integer.valueOf(5), "Viernes"));
/* 152:213 */     lista.add(new SelectItem(Integer.valueOf(6), "Sabado"));
/* 153:214 */     return lista;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public DiaFestivo getDiaFestivo()
/* 157:    */   {
/* 158:218 */     return this.diaFestivo;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void setDiaFestivo(DiaFestivo diaFestivo)
/* 162:    */   {
/* 163:222 */     this.diaFestivo = diaFestivo;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public LazyDataModel<DiaFestivo> getListaDiaFestivo()
/* 167:    */   {
/* 168:226 */     return this.listaDiaFestivo;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public void setListaDiaFestivo(LazyDataModel<DiaFestivo> listaDiaFestivo)
/* 172:    */   {
/* 173:230 */     this.listaDiaFestivo = listaDiaFestivo;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public DataTable getDtDiaFestivo()
/* 177:    */   {
/* 178:234 */     return this.dtDiaFestivo;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public void setDtDiaFestivo(DataTable dtDiaFestivo)
/* 182:    */   {
/* 183:238 */     this.dtDiaFestivo = dtDiaFestivo;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public Integer getAnnoOrigen()
/* 187:    */   {
/* 188:242 */     if (this.annoOrigen == null) {
/* 189:243 */       this.annoOrigen = Integer.valueOf(new Date().getYear() + 1900 - 1);
/* 190:    */     }
/* 191:245 */     return this.annoOrigen;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public void setAnnoOrigen(Integer annoOrigen)
/* 195:    */   {
/* 196:249 */     this.annoOrigen = annoOrigen;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public Integer getAnnoDestino()
/* 200:    */   {
/* 201:253 */     if (this.annoDestino == null) {
/* 202:254 */       this.annoDestino = Integer.valueOf(new Date().getYear() + 1900);
/* 203:    */     }
/* 204:256 */     return this.annoDestino;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public void setAnnoDestino(Integer annoDestino)
/* 208:    */   {
/* 209:260 */     this.annoDestino = annoDestino;
/* 210:    */   }
/* 211:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.asistencia.configuracion.DiaFestivoBean
 * JD-Core Version:    0.7.0.1
 */