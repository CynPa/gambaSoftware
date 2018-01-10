/*   1:    */ package com.asinfo.as2.nomina.procesos;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Empleado;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.entities.RubroOtraEmpresa;
/*   8:    */ import com.asinfo.as2.entities.Sucursal;
/*   9:    */ import com.asinfo.as2.enumeraciones.Mes;
/*  10:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioRubroOtraEmpresa;
/*  11:    */ import com.asinfo.as2.util.AppUtil;
/*  12:    */ import java.util.ArrayList;
/*  13:    */ import java.util.Calendar;
/*  14:    */ import java.util.List;
/*  15:    */ import java.util.Map;
/*  16:    */ import javax.annotation.PostConstruct;
/*  17:    */ import javax.ejb.EJB;
/*  18:    */ import javax.faces.bean.ManagedBean;
/*  19:    */ import javax.faces.bean.ViewScoped;
/*  20:    */ import javax.faces.model.SelectItem;
/*  21:    */ import org.apache.log4j.Logger;
/*  22:    */ import org.primefaces.component.datatable.DataTable;
/*  23:    */ import org.primefaces.model.LazyDataModel;
/*  24:    */ import org.primefaces.model.SortOrder;
/*  25:    */ 
/*  26:    */ @ManagedBean
/*  27:    */ @ViewScoped
/*  28:    */ public class RubroOtraEmpresaBean
/*  29:    */   extends PageControllerAS2
/*  30:    */ {
/*  31:    */   private static final long serialVersionUID = 5658809958470632830L;
/*  32:    */   @EJB
/*  33:    */   private ServicioRubroOtraEmpresa servicioRubroOtraEmpresa;
/*  34:    */   private RubroOtraEmpresa rubroOtraEmpresa;
/*  35:    */   private Empleado empleado;
/*  36:    */   private LazyDataModel<RubroOtraEmpresa> listaRubroOtraEmpresa;
/*  37:    */   private List<SelectItem> listaMes;
/*  38:    */   private DataTable dtRubroOtraEmpresa;
/*  39:    */   
/*  40:    */   @PostConstruct
/*  41:    */   public void init()
/*  42:    */   {
/*  43: 81 */     this.listaRubroOtraEmpresa = new LazyDataModel()
/*  44:    */     {
/*  45:    */       private static final long serialVersionUID = 1L;
/*  46:    */       
/*  47:    */       public List<RubroOtraEmpresa> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  48:    */       {
/*  49: 88 */         List<RubroOtraEmpresa> lista = new ArrayList();
/*  50: 89 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  51: 90 */         filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/*  52: 91 */         lista = RubroOtraEmpresaBean.this.servicioRubroOtraEmpresa.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  53: 92 */         RubroOtraEmpresaBean.this.listaRubroOtraEmpresa.setRowCount(RubroOtraEmpresaBean.this.servicioRubroOtraEmpresa.contarPorCriterio(filters));
/*  54:    */         
/*  55: 94 */         return lista;
/*  56:    */       }
/*  57:    */     };
/*  58:    */   }
/*  59:    */   
/*  60:    */   private void crearRubroOtraEmpresa()
/*  61:    */   {
/*  62:108 */     this.rubroOtraEmpresa = new RubroOtraEmpresa();
/*  63:109 */     this.rubroOtraEmpresa.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  64:110 */     this.rubroOtraEmpresa.setIdSucursal(AppUtil.getSucursal().getId());
/*  65:111 */     this.rubroOtraEmpresa.setEmpleado(new Empleado());
/*  66:112 */     this.rubroOtraEmpresa.setAnio(Calendar.getInstance().get(1));
/*  67:113 */     this.empleado = new Empleado();
/*  68:    */   }
/*  69:    */   
/*  70:    */   public String editar()
/*  71:    */   {
/*  72:122 */     if ((getRubroOtraEmpresa() != null) && (getRubroOtraEmpresa().getIdRubroOtraEmpresa() > 0))
/*  73:    */     {
/*  74:123 */       this.rubroOtraEmpresa = this.servicioRubroOtraEmpresa.cargarDetalle(getRubroOtraEmpresa().getId());
/*  75:124 */       this.empleado = this.rubroOtraEmpresa.getEmpleado();
/*  76:125 */       setEditado(true);
/*  77:    */     }
/*  78:    */     else
/*  79:    */     {
/*  80:127 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  81:    */     }
/*  82:129 */     return "";
/*  83:    */   }
/*  84:    */   
/*  85:    */   public String guardar()
/*  86:    */   {
/*  87:    */     try
/*  88:    */     {
/*  89:139 */       if ((this.rubroOtraEmpresa.getEmpleado().equals(null)) || (this.rubroOtraEmpresa.getEmpleado().getId() == 0))
/*  90:    */       {
/*  91:140 */         addErrorMessage(getLanguageController().getMensaje("msg_info_seleccionar_empleado"));
/*  92:    */       }
/*  93:    */       else
/*  94:    */       {
/*  95:142 */         this.servicioRubroOtraEmpresa.guardar(this.rubroOtraEmpresa);
/*  96:143 */         addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  97:144 */         setEditado(false);
/*  98:145 */         limpiar();
/*  99:    */       }
/* 100:    */     }
/* 101:    */     catch (Exception e)
/* 102:    */     {
/* 103:149 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 104:150 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 105:151 */       e.printStackTrace();
/* 106:    */     }
/* 107:153 */     return "";
/* 108:    */   }
/* 109:    */   
/* 110:    */   public String eliminar()
/* 111:    */   {
/* 112:    */     try
/* 113:    */     {
/* 114:163 */       this.servicioRubroOtraEmpresa.eliminar(this.rubroOtraEmpresa);
/* 115:164 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 116:    */     }
/* 117:    */     catch (Exception e)
/* 118:    */     {
/* 119:166 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 120:167 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 121:    */     }
/* 122:169 */     return "";
/* 123:    */   }
/* 124:    */   
/* 125:    */   public String cargarDatos()
/* 126:    */   {
/* 127:178 */     return "";
/* 128:    */   }
/* 129:    */   
/* 130:    */   public String limpiar()
/* 131:    */   {
/* 132:187 */     crearRubroOtraEmpresa();
/* 133:188 */     return "";
/* 134:    */   }
/* 135:    */   
/* 136:    */   public String cargarEmpleado()
/* 137:    */   {
/* 138:195 */     this.rubroOtraEmpresa.setEmpleado(this.empleado);
/* 139:196 */     return "";
/* 140:    */   }
/* 141:    */   
/* 142:    */   public RubroOtraEmpresa getRubroOtraEmpresa()
/* 143:    */   {
/* 144:209 */     return this.rubroOtraEmpresa;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void setRubroOtraEmpresa(RubroOtraEmpresa rubroOtraEmpresa)
/* 148:    */   {
/* 149:219 */     this.rubroOtraEmpresa = rubroOtraEmpresa;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public LazyDataModel<RubroOtraEmpresa> getListaRubroOtraEmpresa()
/* 153:    */   {
/* 154:228 */     return this.listaRubroOtraEmpresa;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public void setListaRubroOtraEmpresa(LazyDataModel<RubroOtraEmpresa> listaRubroOtraEmpresa)
/* 158:    */   {
/* 159:238 */     this.listaRubroOtraEmpresa = listaRubroOtraEmpresa;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public DataTable getDtRubroOtraEmpresa()
/* 163:    */   {
/* 164:247 */     return this.dtRubroOtraEmpresa;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public void setDtRubroOtraEmpresa(DataTable dtRubroOtraEmpresa)
/* 168:    */   {
/* 169:257 */     this.dtRubroOtraEmpresa = dtRubroOtraEmpresa;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public Empleado getEmpleado()
/* 173:    */   {
/* 174:266 */     return this.empleado;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public void setEmpleado(Empleado empleado)
/* 178:    */   {
/* 179:276 */     this.empleado = empleado;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public List<SelectItem> getListaMes()
/* 183:    */   {
/* 184:283 */     if (this.listaMes == null)
/* 185:    */     {
/* 186:284 */       this.listaMes = new ArrayList();
/* 187:285 */       for (Mes t : Mes.values())
/* 188:    */       {
/* 189:286 */         SelectItem item = new SelectItem(t, t.getNombre());
/* 190:287 */         this.listaMes.add(item);
/* 191:    */       }
/* 192:    */     }
/* 193:290 */     return this.listaMes;
/* 194:    */   }
/* 195:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.procesos.RubroOtraEmpresaBean
 * JD-Core Version:    0.7.0.1
 */