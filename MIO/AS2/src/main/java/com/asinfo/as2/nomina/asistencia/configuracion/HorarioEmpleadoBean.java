/*   1:    */ package com.asinfo.as2.nomina.asistencia.configuracion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.entities.Sucursal;
/*   7:    */ import com.asinfo.as2.entities.nomina.asistencia.HorarioEmpleado;
/*   8:    */ import com.asinfo.as2.entities.nomina.asistencia.Turno;
/*   9:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  10:    */ import com.asinfo.as2.util.AppUtil;
/*  11:    */ import java.util.ArrayList;
/*  12:    */ import java.util.HashMap;
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
/*  26:    */ public class HorarioEmpleadoBean
/*  27:    */   extends PageControllerAS2
/*  28:    */ {
/*  29:    */   private static final long serialVersionUID = -6642782572366933215L;
/*  30:    */   @EJB
/*  31:    */   private ServicioGenerico<HorarioEmpleado> servicioHorarioEmpleado;
/*  32:    */   @EJB
/*  33:    */   private ServicioGenerico<Turno> servicioTurno;
/*  34:    */   @EJB
/*  35:    */   private ServicioAsistenciaConfiguracion servicioAsistenciaConfiguracion;
/*  36:    */   private HorarioEmpleado horarioEmpleado;
/*  37:    */   private LazyDataModel<HorarioEmpleado> listaHorarioEmpleado;
/*  38:    */   private List<Turno> listaTurno;
/*  39:    */   private DataTable dtHorarioEmpleado;
/*  40:    */   
/*  41:    */   @PostConstruct
/*  42:    */   public void init()
/*  43:    */   {
/*  44: 80 */     this.listaHorarioEmpleado = new LazyDataModel()
/*  45:    */     {
/*  46:    */       private static final long serialVersionUID = -1752987002238164010L;
/*  47:    */       
/*  48:    */       public List<HorarioEmpleado> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  49:    */       {
/*  50: 90 */         List<HorarioEmpleado> lista = new ArrayList();
/*  51: 91 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  52:    */         
/*  53: 93 */         lista = HorarioEmpleadoBean.this.servicioHorarioEmpleado.obtenerListaPorPagina(HorarioEmpleado.class, startIndex, pageSize, sortField, ordenar, filters);
/*  54: 94 */         HorarioEmpleadoBean.this.listaHorarioEmpleado.setRowCount(HorarioEmpleadoBean.this.servicioHorarioEmpleado.contarPorCriterio(HorarioEmpleado.class, filters));
/*  55:    */         
/*  56: 96 */         return lista;
/*  57:    */       }
/*  58:    */     };
/*  59:    */   }
/*  60:    */   
/*  61:    */   public String crearHorarioEmpleado()
/*  62:    */   {
/*  63:109 */     this.horarioEmpleado = new HorarioEmpleado();
/*  64:110 */     this.horarioEmpleado.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/*  65:111 */     this.horarioEmpleado.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/*  66:112 */     this.horarioEmpleado.setActivo(true);
/*  67:113 */     return "";
/*  68:    */   }
/*  69:    */   
/*  70:    */   public String limpiar()
/*  71:    */   {
/*  72:122 */     crearHorarioEmpleado();
/*  73:123 */     return "";
/*  74:    */   }
/*  75:    */   
/*  76:    */   public String editar()
/*  77:    */   {
/*  78:132 */     if ((getHorarioEmpleado() != null) && (getHorarioEmpleado().getId() != 0))
/*  79:    */     {
/*  80:133 */       this.horarioEmpleado = this.servicioAsistenciaConfiguracion.cargarDetalleHorarioEmpleado(this.horarioEmpleado);
/*  81:134 */       setEditado(true);
/*  82:    */     }
/*  83:    */     else
/*  84:    */     {
/*  85:136 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  86:    */     }
/*  87:138 */     return "";
/*  88:    */   }
/*  89:    */   
/*  90:    */   public String guardar()
/*  91:    */   {
/*  92:    */     try
/*  93:    */     {
/*  94:148 */       if (getHorarioEmpleado().isIndicadorRotativo())
/*  95:    */       {
/*  96:149 */         this.horarioEmpleado.setTurno1(null);
/*  97:150 */         this.horarioEmpleado.setTurno2(null);
/*  98:151 */         this.horarioEmpleado.setTurno3(null);
/*  99:152 */         this.horarioEmpleado.setTurno4(null);
/* 100:153 */         this.horarioEmpleado.setTurno5(null);
/* 101:154 */         this.horarioEmpleado.setTurno6(null);
/* 102:155 */         this.horarioEmpleado.setTurno0(null);
/* 103:    */       }
/* 104:157 */       this.servicioHorarioEmpleado.guardar(getHorarioEmpleado());
/* 105:158 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 106:159 */       limpiar();
/* 107:160 */       setEditado(false);
/* 108:    */     }
/* 109:    */     catch (Exception e)
/* 110:    */     {
/* 111:162 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 112:163 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 113:    */     }
/* 114:165 */     return "";
/* 115:    */   }
/* 116:    */   
/* 117:    */   public String eliminar()
/* 118:    */   {
/* 119:174 */     if ((getHorarioEmpleado() != null) && (getHorarioEmpleado().getId() != 0)) {
/* 120:    */       try
/* 121:    */       {
/* 122:176 */         this.servicioHorarioEmpleado.eliminar(getHorarioEmpleado());
/* 123:177 */         addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 124:    */       }
/* 125:    */       catch (Exception e)
/* 126:    */       {
/* 127:179 */         addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 128:180 */         LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 129:    */       }
/* 130:    */     } else {
/* 131:183 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 132:    */     }
/* 133:185 */     return "";
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void copiar()
/* 137:    */   {
/* 138:189 */     if ((getHorarioEmpleado() != null) && (getHorarioEmpleado().getId() != 0)) {
/* 139:    */       try
/* 140:    */       {
/* 141:191 */         HorarioEmpleado horarioEmpleadoNew = this.servicioAsistenciaConfiguracion.cargarDetalleHorarioEmpleado(this.horarioEmpleado);
/* 142:192 */         horarioEmpleadoNew.setIdHorarioEmpleado(0);
/* 143:193 */         horarioEmpleadoNew.setCodigo(horarioEmpleadoNew.getCodigo() + "_2");
/* 144:194 */         horarioEmpleadoNew.setNombre(horarioEmpleadoNew.getNombre() + "_2");
/* 145:195 */         this.servicioHorarioEmpleado.guardar(horarioEmpleadoNew);
/* 146:    */       }
/* 147:    */       catch (Exception e)
/* 148:    */       {
/* 149:197 */         addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 150:198 */         LOG.error("ERROR AL GUARDAR DATOS", e);
/* 151:    */       }
/* 152:    */     } else {
/* 153:201 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 154:    */     }
/* 155:    */   }
/* 156:    */   
/* 157:    */   public String cargarDatos()
/* 158:    */   {
/* 159:211 */     return "";
/* 160:    */   }
/* 161:    */   
/* 162:    */   public HorarioEmpleado getHorarioEmpleado()
/* 163:    */   {
/* 164:219 */     return this.horarioEmpleado;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public void setHorarioEmpleado(HorarioEmpleado horarioEmpleado)
/* 168:    */   {
/* 169:223 */     this.horarioEmpleado = horarioEmpleado;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public LazyDataModel<HorarioEmpleado> getListaHorarioEmpleado()
/* 173:    */   {
/* 174:227 */     return this.listaHorarioEmpleado;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public void setListaHorarioEmpleado(LazyDataModel<HorarioEmpleado> listaHorarioEmpleado)
/* 178:    */   {
/* 179:231 */     this.listaHorarioEmpleado = listaHorarioEmpleado;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public DataTable getDtHorarioEmpleado()
/* 183:    */   {
/* 184:235 */     return this.dtHorarioEmpleado;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public void setDtHorarioEmpleado(DataTable dtHorarioEmpleado)
/* 188:    */   {
/* 189:239 */     this.dtHorarioEmpleado = dtHorarioEmpleado;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public List<Turno> getListaTurno()
/* 193:    */   {
/* 194:243 */     if (this.listaTurno == null)
/* 195:    */     {
/* 196:244 */       Map<String, String> filtros = new HashMap();
/* 197:245 */       filtros.put("activo", "=true");
/* 198:246 */       filtros.put("idOrganizacion", "" + AppUtil.getOrganizacion().getId());
/* 199:247 */       this.listaTurno = this.servicioTurno.obtenerListaCombo(Turno.class, "nombre", true, filtros);
/* 200:    */     }
/* 201:249 */     return this.listaTurno;
/* 202:    */   }
/* 203:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.asistencia.configuracion.HorarioEmpleadoBean
 * JD-Core Version:    0.7.0.1
 */