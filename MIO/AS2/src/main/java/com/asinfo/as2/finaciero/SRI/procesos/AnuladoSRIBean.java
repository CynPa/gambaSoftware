/*   1:    */ package com.asinfo.as2.finaciero.SRI.procesos;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.entities.Sucursal;
/*   7:    */ import com.asinfo.as2.entities.sri.AnuladoSRI;
/*   8:    */ import com.asinfo.as2.entities.sri.TipoComprobanteSRI;
/*   9:    */ import com.asinfo.as2.enumeraciones.Mes;
/*  10:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  11:    */ import com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioAnuladoSRI;
/*  12:    */ import com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioSRI;
/*  13:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  14:    */ import com.asinfo.as2.util.AppUtil;
/*  15:    */ import java.util.ArrayList;
/*  16:    */ import java.util.List;
/*  17:    */ import java.util.Map;
/*  18:    */ import javax.annotation.PostConstruct;
/*  19:    */ import javax.ejb.EJB;
/*  20:    */ import javax.faces.bean.ManagedBean;
/*  21:    */ import javax.faces.bean.ViewScoped;
/*  22:    */ import javax.faces.model.SelectItem;
/*  23:    */ import org.apache.log4j.Logger;
/*  24:    */ import org.primefaces.component.datatable.DataTable;
/*  25:    */ import org.primefaces.model.LazyDataModel;
/*  26:    */ import org.primefaces.model.SortOrder;
/*  27:    */ 
/*  28:    */ @ManagedBean
/*  29:    */ @ViewScoped
/*  30:    */ public class AnuladoSRIBean
/*  31:    */   extends PageControllerAS2
/*  32:    */ {
/*  33:    */   private static final long serialVersionUID = 8782410859351300688L;
/*  34:    */   @EJB
/*  35:    */   private ServicioAnuladoSRI servicioAnuladoSRI;
/*  36:    */   @EJB
/*  37:    */   private ServicioSRI servicioSRI;
/*  38:    */   private AnuladoSRI anuladoSRI;
/*  39:    */   private String tipoComprobanteSRISeleccionado;
/*  40:    */   private List<TipoComprobanteSRI> listaTipoComprobanteSRI;
/*  41:    */   private LazyDataModel<AnuladoSRI> listaAnuladoSRI;
/*  42:    */   private List<SelectItem> listaMes;
/*  43:    */   private DataTable dtAnuladoSRI;
/*  44:    */   
/*  45:    */   @PostConstruct
/*  46:    */   public void init()
/*  47:    */   {
/*  48: 86 */     this.listaAnuladoSRI = new LazyDataModel()
/*  49:    */     {
/*  50:    */       private static final long serialVersionUID = 1L;
/*  51:    */       
/*  52:    */       public List<AnuladoSRI> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  53:    */       {
/*  54: 93 */         List<AnuladoSRI> lista = new ArrayList();
/*  55: 94 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  56:    */         
/*  57: 96 */         lista = AnuladoSRIBean.this.servicioAnuladoSRI.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  58:    */         
/*  59: 98 */         AnuladoSRIBean.this.listaAnuladoSRI.setRowCount(AnuladoSRIBean.this.servicioAnuladoSRI.contarPorCriterio(filters));
/*  60: 99 */         return lista;
/*  61:    */       }
/*  62:    */     };
/*  63:    */   }
/*  64:    */   
/*  65:    */   private void crearEntidad()
/*  66:    */   {
/*  67:113 */     this.anuladoSRI = new AnuladoSRI();
/*  68:114 */     this.anuladoSRI.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/*  69:115 */     this.anuladoSRI.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/*  70:116 */     this.anuladoSRI.setTipoComprobanteSRI(new TipoComprobanteSRI());
/*  71:    */   }
/*  72:    */   
/*  73:    */   public String editar()
/*  74:    */   {
/*  75:125 */     if (getAnuladoSRI().getIdAnuladoSRI() > 0) {
/*  76:126 */       setEditado(true);
/*  77:    */     } else {
/*  78:128 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  79:    */     }
/*  80:130 */     return "";
/*  81:    */   }
/*  82:    */   
/*  83:    */   public String guardar()
/*  84:    */   {
/*  85:    */     try
/*  86:    */     {
/*  87:140 */       this.servicioAnuladoSRI.guardar(this.anuladoSRI);
/*  88:141 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  89:142 */       setEditado(false);
/*  90:143 */       limpiar();
/*  91:    */     }
/*  92:    */     catch (ExcepcionAS2Financiero e)
/*  93:    */     {
/*  94:145 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  95:146 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  96:    */     }
/*  97:    */     catch (Exception e)
/*  98:    */     {
/*  99:148 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 100:149 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 101:    */     }
/* 102:151 */     return "";
/* 103:    */   }
/* 104:    */   
/* 105:    */   public String eliminar()
/* 106:    */   {
/* 107:    */     try
/* 108:    */     {
/* 109:161 */       this.servicioAnuladoSRI.eliminar(this.anuladoSRI);
/* 110:162 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 111:    */     }
/* 112:    */     catch (Exception e)
/* 113:    */     {
/* 114:164 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 115:165 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 116:    */     }
/* 117:167 */     return "";
/* 118:    */   }
/* 119:    */   
/* 120:    */   public String cargarDatos()
/* 121:    */   {
/* 122:176 */     return "";
/* 123:    */   }
/* 124:    */   
/* 125:    */   public List<SelectItem> getListaMes()
/* 126:    */   {
/* 127:181 */     if (this.listaMes == null)
/* 128:    */     {
/* 129:182 */       this.listaMes = new ArrayList();
/* 130:183 */       for (Mes t : Mes.values())
/* 131:    */       {
/* 132:184 */         SelectItem item = new SelectItem(Integer.valueOf(t.ordinal() + 1), t.toString());
/* 133:185 */         this.listaMes.add(item);
/* 134:    */       }
/* 135:    */     }
/* 136:188 */     return this.listaMes;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public String copiarValorDesdeHasta()
/* 140:    */   {
/* 141:197 */     if (getAnuladoSRI().getNumeroDesde() == null) {
/* 142:198 */       getAnuladoSRI().setNumeroDesde("0");
/* 143:    */     }
/* 144:200 */     if (getAnuladoSRI().getNumeroHasta() == null) {
/* 145:201 */       getAnuladoSRI().setNumeroHasta("0");
/* 146:    */     }
/* 147:203 */     if ((getAnuladoSRI().getNumeroHasta().equals("0")) || (getAnuladoSRI().getNumeroHasta().isEmpty()) || 
/* 148:204 */       (Integer.parseInt(getAnuladoSRI().getNumeroDesde()) > Integer.parseInt(getAnuladoSRI().getNumeroHasta()))) {
/* 149:205 */       getAnuladoSRI().setNumeroHasta(getAnuladoSRI().getNumeroDesde());
/* 150:    */     }
/* 151:207 */     return "";
/* 152:    */   }
/* 153:    */   
/* 154:    */   public String limpiar()
/* 155:    */   {
/* 156:216 */     crearEntidad();
/* 157:217 */     return "";
/* 158:    */   }
/* 159:    */   
/* 160:    */   public AnuladoSRI getAnuladoSRI()
/* 161:    */   {
/* 162:234 */     if (this.anuladoSRI == null) {
/* 163:235 */       this.anuladoSRI = new AnuladoSRI();
/* 164:    */     }
/* 165:237 */     return this.anuladoSRI;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public void setAnuladoSRI(AnuladoSRI anuladoSRI)
/* 169:    */   {
/* 170:247 */     this.anuladoSRI = anuladoSRI;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public LazyDataModel<AnuladoSRI> getListaAnuladoSRI()
/* 174:    */   {
/* 175:256 */     return this.listaAnuladoSRI;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public void setListaAnuladoSRI(LazyDataModel<AnuladoSRI> listaAnuladoSRI)
/* 179:    */   {
/* 180:266 */     this.listaAnuladoSRI = listaAnuladoSRI;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public DataTable getDtAnuladoSRI()
/* 184:    */   {
/* 185:275 */     return this.dtAnuladoSRI;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public void setDtAnuladoSRI(DataTable dtAnuladoSRI)
/* 189:    */   {
/* 190:285 */     this.dtAnuladoSRI = dtAnuladoSRI;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public String getTipoComprobanteSRISeleccionado()
/* 194:    */   {
/* 195:290 */     this.tipoComprobanteSRISeleccionado = "";
/* 196:292 */     if (getAnuladoSRI().getTipoComprobanteSRI() != null) {
/* 197:293 */       this.tipoComprobanteSRISeleccionado = ("" + getAnuladoSRI().getTipoComprobanteSRI().getId());
/* 198:    */     }
/* 199:296 */     return this.tipoComprobanteSRISeleccionado;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public void setTipoComprobanteSRISeleccionado(String tipoComprobanteSRISeleccionado)
/* 203:    */     throws NumberFormatException, ExcepcionAS2
/* 204:    */   {
/* 205:309 */     if (!this.tipoComprobanteSRISeleccionado.equals(tipoComprobanteSRISeleccionado))
/* 206:    */     {
/* 207:310 */       this.tipoComprobanteSRISeleccionado = tipoComprobanteSRISeleccionado;
/* 208:    */       
/* 209:312 */       TipoComprobanteSRI tipoComprobanteSRI = null;
/* 210:313 */       if (!tipoComprobanteSRISeleccionado.isEmpty()) {
/* 211:314 */         tipoComprobanteSRI = this.servicioSRI.buscarTipoComprobanteSRIPorId(Integer.parseInt(this.tipoComprobanteSRISeleccionado));
/* 212:    */       }
/* 213:316 */       getAnuladoSRI().setTipoComprobanteSRI(tipoComprobanteSRI);
/* 214:    */     }
/* 215:319 */     this.tipoComprobanteSRISeleccionado = tipoComprobanteSRISeleccionado;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public List<TipoComprobanteSRI> getListaTipoComprobanteSRI()
/* 219:    */   {
/* 220:328 */     if (this.listaTipoComprobanteSRI == null) {
/* 221:329 */       this.listaTipoComprobanteSRI = this.servicioSRI.obtenerTipoComprobanteSRI();
/* 222:    */     }
/* 223:331 */     return this.listaTipoComprobanteSRI;
/* 224:    */   }
/* 225:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.SRI.procesos.AnuladoSRIBean
 * JD-Core Version:    0.7.0.1
 */