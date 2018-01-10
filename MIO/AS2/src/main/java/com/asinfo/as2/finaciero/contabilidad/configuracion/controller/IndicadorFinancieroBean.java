/*   1:    */ package com.asinfo.as2.finaciero.contabilidad.configuracion.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.IndicadorFinanciero;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.entities.Sucursal;
/*   8:    */ import com.asinfo.as2.entities.Variable;
/*   9:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioIndicadorFinanciero;
/*  10:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioVariable;
/*  11:    */ import com.asinfo.as2.nomina.procesos.compilador.Evaluador;
/*  12:    */ import com.asinfo.as2.util.AppUtil;
/*  13:    */ import com.asinfo.as2.utils.formula.Identificador;
/*  14:    */ import java.util.ArrayList;
/*  15:    */ import java.util.List;
/*  16:    */ import java.util.Map;
/*  17:    */ import javax.annotation.PostConstruct;
/*  18:    */ import javax.ejb.EJB;
/*  19:    */ import javax.faces.bean.ManagedBean;
/*  20:    */ import javax.faces.bean.ViewScoped;
/*  21:    */ import org.apache.log4j.Logger;
/*  22:    */ import org.primefaces.component.datatable.DataTable;
/*  23:    */ import org.primefaces.model.LazyDataModel;
/*  24:    */ import org.primefaces.model.SortOrder;
/*  25:    */ 
/*  26:    */ @ManagedBean
/*  27:    */ @ViewScoped
/*  28:    */ public class IndicadorFinancieroBean
/*  29:    */   extends PageControllerAS2
/*  30:    */ {
/*  31:    */   private static final long serialVersionUID = 1L;
/*  32:    */   @EJB
/*  33:    */   private ServicioIndicadorFinanciero servicioIndicadorFinanciero;
/*  34:    */   @EJB
/*  35:    */   private ServicioVariable servicioVariable;
/*  36:    */   private IndicadorFinanciero indicadorFinanciero;
/*  37: 63 */   private Evaluador evaluador = new Evaluador();
/*  38:    */   private DataTable dtIndicadorFinanciero;
/*  39:    */   private LazyDataModel<IndicadorFinanciero> listaIndicadorFinanciero;
/*  40:    */   private List<Identificador> listaVariables;
/*  41:    */   private List<Variable> listaVariableLoad;
/*  42:    */   private Integer idIndicadorFinanciero;
/*  43:    */   private String formulaSelecionada;
/*  44:    */   private static final String NOMBRE = "nombre";
/*  45:    */   
/*  46:    */   @PostConstruct
/*  47:    */   public void init()
/*  48:    */   {
/*  49: 78 */     this.listaIndicadorFinanciero = new LazyDataModel()
/*  50:    */     {
/*  51:    */       public List<IndicadorFinanciero> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  52:    */       {
/*  53: 84 */         List<IndicadorFinanciero> lista = new ArrayList();
/*  54: 85 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  55: 86 */         filters.put("idOrganizacion", String.valueOf(AppUtil.getOrganizacion().getIdOrganizacion()));
/*  56: 87 */         lista = IndicadorFinancieroBean.this.servicioIndicadorFinanciero.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  57: 88 */         IndicadorFinancieroBean.this.listaIndicadorFinanciero.setRowCount(IndicadorFinancieroBean.this.servicioIndicadorFinanciero.contarPorCriterio(filters));
/*  58: 89 */         return lista;
/*  59:    */       }
/*  60:    */     };
/*  61:    */   }
/*  62:    */   
/*  63:    */   public String editar()
/*  64:    */   {
/*  65: 97 */     if ((getIndicadorFinanciero() != null) && (getIndicadorFinanciero().getIdIndicadorFinanciero() != 0)) {
/*  66: 98 */       setEditado(true);
/*  67:    */     } else {
/*  68:100 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  69:    */     }
/*  70:102 */     return "";
/*  71:    */   }
/*  72:    */   
/*  73:    */   public String guardar()
/*  74:    */   {
/*  75:    */     try
/*  76:    */     {
/*  77:111 */       List<Variable> auxListaVariable = this.servicioVariable.obtenerListaCombo("", true, null);
/*  78:112 */       String[] expresion = this.indicadorFinanciero.getExpresion().split("[+-/\\(\\)\\*1234567890]");
/*  79:113 */       boolean correcto = true;
/*  80:114 */       for (int i = 0; i < expresion.length; i++)
/*  81:    */       {
/*  82:115 */         if (!expresion[i].isEmpty()) {
/*  83:116 */           for (Variable auxVariable : auxListaVariable)
/*  84:    */           {
/*  85:117 */             if (expresion[i].equals(auxVariable.getNombre()))
/*  86:    */             {
/*  87:118 */               correcto = true;
/*  88:119 */               break;
/*  89:    */             }
/*  90:121 */             correcto = false;
/*  91:    */           }
/*  92:    */         }
/*  93:125 */         if (!correcto) {
/*  94:    */           break;
/*  95:    */         }
/*  96:    */       }
/*  97:130 */       if (correcto)
/*  98:    */       {
/*  99:131 */         this.indicadorFinanciero.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 100:132 */         this.indicadorFinanciero.setIdSucursal(AppUtil.getSucursal().getId());
/* 101:133 */         this.servicioIndicadorFinanciero.guardar(this.indicadorFinanciero);
/* 102:134 */         addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 103:135 */         setEditado(false);
/* 104:136 */         limpiar();
/* 105:    */       }
/* 106:    */       else
/* 107:    */       {
/* 108:138 */         addInfoMessage(getLanguageController().getMensaje("msg_error_formula"));
/* 109:    */       }
/* 110:    */     }
/* 111:    */     catch (Exception e)
/* 112:    */     {
/* 113:141 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 114:142 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 115:    */     }
/* 116:144 */     return "";
/* 117:    */   }
/* 118:    */   
/* 119:    */   public String eliminar()
/* 120:    */   {
/* 121:    */     try
/* 122:    */     {
/* 123:150 */       this.indicadorFinanciero.setEliminado(true);
/* 124:151 */       this.servicioIndicadorFinanciero.guardar(this.indicadorFinanciero);
/* 125:152 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 126:    */     }
/* 127:    */     catch (Exception e)
/* 128:    */     {
/* 129:154 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 130:155 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 131:    */     }
/* 132:157 */     return "";
/* 133:    */   }
/* 134:    */   
/* 135:    */   public String limpiar()
/* 136:    */   {
/* 137:162 */     this.indicadorFinanciero = new IndicadorFinanciero();
/* 138:163 */     this.indicadorFinanciero.setExpresion(new String());
/* 139:164 */     this.indicadorFinanciero.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 140:165 */     this.indicadorFinanciero.setIdSucursal(AppUtil.getSucursal().getId());
/* 141:    */     
/* 142:167 */     return "";
/* 143:    */   }
/* 144:    */   
/* 145:    */   public String cargarDatos()
/* 146:    */   {
/* 147:172 */     setEditado(false);
/* 148:    */     try
/* 149:    */     {
/* 150:175 */       limpiar();
/* 151:    */     }
/* 152:    */     catch (Exception e)
/* 153:    */     {
/* 154:178 */       LOG.error("ERROR AL CARGAR DATOS", e);
/* 155:179 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 156:    */     }
/* 157:181 */     return "";
/* 158:    */   }
/* 159:    */   
/* 160:    */   public List<Identificador> getListaVariables()
/* 161:    */   {
/* 162:191 */     if (this.listaVariables == null)
/* 163:    */     {
/* 164:192 */       this.listaVariables = new ArrayList();
/* 165:194 */       for (Variable variable : getListaVariableLoad())
/* 166:    */       {
/* 167:196 */         Identificador identificador = new Identificador();
/* 168:197 */         identificador.setLabel(variable.getNombre());
/* 169:198 */         identificador.setValue(variable.getNombre());
/* 170:199 */         this.listaVariables.add(identificador);
/* 171:    */       }
/* 172:    */     }
/* 173:202 */     return this.listaVariables;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public void cargarFormula()
/* 177:    */   {
/* 178:209 */     getIndicadorFinanciero().setExpresion(getIndicadorFinanciero().getExpresion().concat(getFormulaSelecionada()));
/* 179:    */   }
/* 180:    */   
/* 181:    */   public String codificarFormula()
/* 182:    */   {
/* 183:220 */     String formulaCodificada = null;
/* 184:221 */     String formulaOriginal = getIndicadorFinanciero().getExpresion();
/* 185:222 */     for (Identificador identificador : getListaVariables())
/* 186:    */     {
/* 187:223 */       formulaCodificada = formulaOriginal.replace(identificador.getLabel().toString(), identificador.getValue().toString());
/* 188:224 */       formulaOriginal = formulaCodificada;
/* 189:    */     }
/* 190:227 */     return formulaCodificada;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public String decodificarFormula()
/* 194:    */   {
/* 195:236 */     String formulaDecodificada = null;
/* 196:237 */     String formulaOriginal = getIndicadorFinanciero().getExpresion();
/* 197:238 */     for (Identificador identificador : getListaVariables())
/* 198:    */     {
/* 199:239 */       formulaDecodificada = formulaOriginal.replace(identificador.getValue().toString(), identificador.getLabel().toString());
/* 200:240 */       formulaOriginal = formulaDecodificada;
/* 201:    */     }
/* 202:243 */     return formulaDecodificada;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public void validaFormula()
/* 206:    */   {
/* 207:255 */     if ((this.evaluador.iAnalizaSintaxis(codificarFormula()) == 0) || (this.evaluador.iAnalizaSintaxis(codificarFormula()) == 14)) {
/* 208:256 */       addInfoMessage(getLanguageController().getMensaje("msg_info_formula"));
/* 209:    */     } else {
/* 210:259 */       addErrorMessage(getLanguageController().getMensaje("msg_error_formula"));
/* 211:    */     }
/* 212:    */   }
/* 213:    */   
/* 214:    */   public DataTable getDtIndicadorFinanciero()
/* 215:    */   {
/* 216:265 */     return this.dtIndicadorFinanciero;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public void setDtIndicadorFinanciero(DataTable dtIndicadorFinanciero)
/* 220:    */   {
/* 221:269 */     this.dtIndicadorFinanciero = dtIndicadorFinanciero;
/* 222:    */   }
/* 223:    */   
/* 224:    */   public IndicadorFinanciero getIndicadorFinanciero()
/* 225:    */   {
/* 226:273 */     return this.indicadorFinanciero;
/* 227:    */   }
/* 228:    */   
/* 229:    */   public void setIndicadorFinanciero(IndicadorFinanciero indicadorFinanciero)
/* 230:    */   {
/* 231:277 */     this.indicadorFinanciero = indicadorFinanciero;
/* 232:    */   }
/* 233:    */   
/* 234:    */   public LazyDataModel<IndicadorFinanciero> getListaIndicadorFinanciero()
/* 235:    */   {
/* 236:281 */     return this.listaIndicadorFinanciero;
/* 237:    */   }
/* 238:    */   
/* 239:    */   public void setListaIndicadorFinanciero(LazyDataModel<IndicadorFinanciero> listaIndicadorFinanciero)
/* 240:    */   {
/* 241:286 */     this.listaIndicadorFinanciero = listaIndicadorFinanciero;
/* 242:    */   }
/* 243:    */   
/* 244:    */   public String getFormulaSelecionada()
/* 245:    */   {
/* 246:291 */     return this.formulaSelecionada;
/* 247:    */   }
/* 248:    */   
/* 249:    */   public void setFormulaSelecionada(String formulaSelecionada)
/* 250:    */   {
/* 251:296 */     this.formulaSelecionada = formulaSelecionada;
/* 252:    */   }
/* 253:    */   
/* 254:    */   public Evaluador getEvaluador()
/* 255:    */   {
/* 256:301 */     return this.evaluador;
/* 257:    */   }
/* 258:    */   
/* 259:    */   public void setEvaluador(Evaluador evaluador)
/* 260:    */   {
/* 261:306 */     this.evaluador = evaluador;
/* 262:    */   }
/* 263:    */   
/* 264:    */   public List<Variable> getListaVariableLoad()
/* 265:    */   {
/* 266:311 */     this.listaVariableLoad = this.servicioVariable.obtenerListaCombo("nombre", true, null);
/* 267:312 */     return this.listaVariableLoad;
/* 268:    */   }
/* 269:    */   
/* 270:    */   public void setListaVariableLoad(List<Variable> listaVariableLoad)
/* 271:    */   {
/* 272:317 */     this.listaVariableLoad = listaVariableLoad;
/* 273:    */   }
/* 274:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.contabilidad.configuracion.controller.IndicadorFinancieroBean
 * JD-Core Version:    0.7.0.1
 */