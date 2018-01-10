/*   1:    */ package com.asinfo.as2.finaciero.pagos.configuracion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.EstadoCheque;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.entities.Sucursal;
/*   8:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   9:    */ import com.asinfo.as2.financiero.pagos.configuracion.servicio.ServicioEstadoCheque;
/*  10:    */ import com.asinfo.as2.util.AppUtil;
/*  11:    */ import java.util.ArrayList;
/*  12:    */ import java.util.List;
/*  13:    */ import java.util.Map;
/*  14:    */ import javax.annotation.PostConstruct;
/*  15:    */ import javax.ejb.EJB;
/*  16:    */ import javax.faces.bean.ManagedBean;
/*  17:    */ import javax.faces.bean.ViewScoped;
/*  18:    */ import javax.faces.model.SelectItem;
/*  19:    */ import org.apache.log4j.Logger;
/*  20:    */ import org.primefaces.component.datatable.DataTable;
/*  21:    */ import org.primefaces.event.SelectEvent;
/*  22:    */ import org.primefaces.model.LazyDataModel;
/*  23:    */ import org.primefaces.model.SortOrder;
/*  24:    */ 
/*  25:    */ @ManagedBean
/*  26:    */ @ViewScoped
/*  27:    */ public class EstadoChequeBean
/*  28:    */   extends PageControllerAS2
/*  29:    */ {
/*  30:    */   private static final long serialVersionUID = 1L;
/*  31:    */   @EJB
/*  32:    */   private ServicioEstadoCheque servicioEstadoCheque;
/*  33:    */   private EstadoCheque estadoCheque;
/*  34:    */   private LazyDataModel<EstadoCheque> listaEstadoCheque;
/*  35:    */   private List<SelectItem> estadoChequeItems;
/*  36:    */   private DataTable dtEstadoCheque;
/*  37:    */   
/*  38:    */   @PostConstruct
/*  39:    */   public void init()
/*  40:    */   {
/*  41: 53 */     this.listaEstadoCheque = new LazyDataModel()
/*  42:    */     {
/*  43:    */       private static final long serialVersionUID = 1L;
/*  44:    */       
/*  45:    */       public List<EstadoCheque> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  46:    */       {
/*  47: 60 */         List<EstadoCheque> lista = new ArrayList();
/*  48: 61 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  49:    */         
/*  50: 63 */         lista = EstadoChequeBean.this.servicioEstadoCheque.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  51: 64 */         EstadoChequeBean.this.listaEstadoCheque.setRowCount(EstadoChequeBean.this.servicioEstadoCheque.contarPorCriterio(filters));
/*  52:    */         
/*  53: 66 */         return lista;
/*  54:    */       }
/*  55:    */     };
/*  56:    */   }
/*  57:    */   
/*  58:    */   public String editar()
/*  59:    */   {
/*  60: 79 */     if (getEstadoCheque().getId() > 0)
/*  61:    */     {
/*  62: 80 */       if ((getEstadoCheque().isEstadoInicial()) || (getEstadoCheque().isEstadoFinal()))
/*  63:    */       {
/*  64: 81 */         addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  65: 82 */         return "";
/*  66:    */       }
/*  67: 84 */       setEditado(true);
/*  68:    */     }
/*  69:    */     else
/*  70:    */     {
/*  71: 86 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  72:    */     }
/*  73: 88 */     return "";
/*  74:    */   }
/*  75:    */   
/*  76:    */   public String guardar()
/*  77:    */   {
/*  78:    */     try
/*  79:    */     {
/*  80: 99 */       this.servicioEstadoCheque.guardar(this.estadoCheque);
/*  81:100 */       limpiar();
/*  82:101 */       setEditado(false);
/*  83:102 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  84:    */     }
/*  85:    */     catch (ExcepcionAS2 e)
/*  86:    */     {
/*  87:104 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  88:    */     }
/*  89:    */     catch (Exception e)
/*  90:    */     {
/*  91:106 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  92:107 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  93:    */     }
/*  94:109 */     return "";
/*  95:    */   }
/*  96:    */   
/*  97:    */   public String limpiar()
/*  98:    */   {
/*  99:119 */     crearEstadoCheque();
/* 100:120 */     return "";
/* 101:    */   }
/* 102:    */   
/* 103:    */   public String eliminar()
/* 104:    */   {
/* 105:    */     try
/* 106:    */     {
/* 107:131 */       if ((getEstadoCheque().isEstadoInicial()) || (getEstadoCheque().isEstadoFinal()))
/* 108:    */       {
/* 109:132 */         addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 110:133 */         return "";
/* 111:    */       }
/* 112:135 */       this.servicioEstadoCheque.eliminar(this.estadoCheque);
/* 113:136 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 114:    */     }
/* 115:    */     catch (Exception e)
/* 116:    */     {
/* 117:138 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 118:139 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 119:    */     }
/* 120:141 */     return "";
/* 121:    */   }
/* 122:    */   
/* 123:    */   public String cargarDatos()
/* 124:    */   {
/* 125:151 */     return "";
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void cargarDatosEstadoCheque()
/* 129:    */   {
/* 130:158 */     List<EstadoCheque> lEstadoCheque = new ArrayList();
/* 131:159 */     lEstadoCheque = this.servicioEstadoCheque.obtenerListaCombo("nombre", true, null);
/* 132:160 */     this.estadoChequeItems = new ArrayList();
/* 133:162 */     for (EstadoCheque EstadoChequeX : lEstadoCheque)
/* 134:    */     {
/* 135:163 */       int value = EstadoChequeX.getIdEstadoCheque();
/* 136:164 */       String label = EstadoChequeX.getNombre();
/* 137:165 */       SelectItem opcion = new SelectItem(Integer.valueOf(value), label);
/* 138:166 */       this.estadoChequeItems.add(opcion);
/* 139:    */     }
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void crearEstadoCheque()
/* 143:    */   {
/* 144:175 */     this.estadoCheque = new EstadoCheque();
/* 145:176 */     this.estadoCheque.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 146:177 */     this.estadoCheque.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void onRowSelect(SelectEvent event)
/* 150:    */   {
/* 151:184 */     EstadoCheque estadoCheque = (EstadoCheque)event.getObject();
/* 152:185 */     setEstadoCheque(estadoCheque);
/* 153:    */   }
/* 154:    */   
/* 155:    */   public EstadoCheque getEstadoCheque()
/* 156:    */   {
/* 157:194 */     if (this.estadoCheque == null) {
/* 158:195 */       crearEstadoCheque();
/* 159:    */     }
/* 160:197 */     return this.estadoCheque;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void setEstadoCheque(EstadoCheque EstadoCheque)
/* 164:    */   {
/* 165:207 */     this.estadoCheque = EstadoCheque;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public LazyDataModel<EstadoCheque> getListaEstadoCheque()
/* 169:    */   {
/* 170:216 */     return this.listaEstadoCheque;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public void setListaEstadoCheque(LazyDataModel<EstadoCheque> listaEstadoCheque)
/* 174:    */   {
/* 175:226 */     this.listaEstadoCheque = listaEstadoCheque;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public List<SelectItem> getEstadoChequeItems()
/* 179:    */   {
/* 180:235 */     return this.estadoChequeItems;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public void setEstadoChequeItems(List<SelectItem> EstadoChequeItems)
/* 184:    */   {
/* 185:245 */     this.estadoChequeItems = EstadoChequeItems;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public DataTable getDtEstadoCheque()
/* 189:    */   {
/* 190:254 */     return this.dtEstadoCheque;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public void setDtEstadoCheque(DataTable dtEstadoCheque)
/* 194:    */   {
/* 195:264 */     this.dtEstadoCheque = dtEstadoCheque;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public void validarEstadoInicial()
/* 199:    */   {
/* 200:268 */     this.estadoCheque.setEstadoFinal(false);
/* 201:    */   }
/* 202:    */   
/* 203:    */   public void validarEstadoFinal()
/* 204:    */   {
/* 205:272 */     this.estadoCheque.setEstadoInicial(false);
/* 206:    */   }
/* 207:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.pagos.configuracion.EstadoChequeBean
 * JD-Core Version:    0.7.0.1
 */