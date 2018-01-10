/*   1:    */ package com.asinfo.as2.finaciero.contabilidad.configuracion.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.PageController;
/*   4:    */ import com.asinfo.as2.entities.DimensionContable;
/*   5:    */ import com.asinfo.as2.entities.UsuarioDimensionContable;
/*   6:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioDimensionContable;
/*   7:    */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*   8:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*   9:    */ import com.asinfo.as2.util.AppUtil;
/*  10:    */ import java.util.ArrayList;
/*  11:    */ import java.util.HashMap;
/*  12:    */ import java.util.List;
/*  13:    */ import java.util.Map;
/*  14:    */ import javax.annotation.PostConstruct;
/*  15:    */ import javax.ejb.EJB;
/*  16:    */ import javax.faces.bean.ManagedBean;
/*  17:    */ import javax.faces.bean.SessionScoped;
/*  18:    */ import org.primefaces.context.RequestContext;
/*  19:    */ import org.primefaces.model.LazyDataModel;
/*  20:    */ import org.primefaces.model.SortOrder;
/*  21:    */ 
/*  22:    */ @ManagedBean
/*  23:    */ @SessionScoped
/*  24:    */ public class ListaDimensionContableBean
/*  25:    */   extends PageController
/*  26:    */ {
/*  27:    */   private static final long serialVersionUID = 6583450793826204104L;
/*  28:    */   @EJB
/*  29:    */   private ServicioDimensionContable servicioDimensionContable;
/*  30:    */   @EJB
/*  31:    */   private ServicioGenerico<UsuarioDimensionContable> servicioUsuarioDimensionContable;
/*  32:    */   private DimensionContable dimensionContable;
/*  33:    */   private LazyDataModel<DimensionContable> listaDimensionContable;
/*  34: 60 */   private String numeroDimension = "1";
/*  35: 61 */   private boolean indicadorSeleccionarTodo = false;
/*  36: 62 */   private boolean porUsuario = false;
/*  37:    */   
/*  38:    */   @PostConstruct
/*  39:    */   public void init()
/*  40:    */   {
/*  41: 72 */     this.listaDimensionContable = new LazyDataModel()
/*  42:    */     {
/*  43:    */       private static final long serialVersionUID = 1L;
/*  44:    */       
/*  45:    */       public List<DimensionContable> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  46:    */       {
/*  47: 78 */         List<DimensionContable> lista = new ArrayList();
/*  48:    */         
/*  49: 80 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  50: 81 */         filters.put("numero", ListaDimensionContableBean.this.numeroDimension);
/*  51: 83 */         if (ListaDimensionContableBean.this.isPorUsuario())
/*  52:    */         {
/*  53: 86 */           Map<String, String> filtersPorUsuario = new HashMap();
/*  54: 87 */           for (String filter : filters.keySet()) {
/*  55: 88 */             if (filter.equals("numero")) {
/*  56: 89 */               filtersPorUsuario.put("dimensionContable.numero", filters.get("numero"));
/*  57: 90 */             } else if (filter.equals("codigo")) {
/*  58: 91 */               filtersPorUsuario.put("dimensionContable.codigo", filters.get("codigo"));
/*  59: 92 */             } else if (filter.equals("nombre")) {
/*  60: 93 */               filtersPorUsuario.put("dimensionContable.nombre", filters.get("nombre"));
/*  61:    */             }
/*  62:    */           }
/*  63: 96 */           filtersPorUsuario.put("entidadUsuario.idUsuario", "=" + AppUtil.getUsuarioEnSesion().getIdUsuario());
/*  64:    */           
/*  65:    */ 
/*  66: 99 */           String sortFieldPorUsuario = null;
/*  67:100 */           if (sortField.equals("codigo")) {
/*  68:101 */             sortFieldPorUsuario = "dimensionContable.codigo";
/*  69:102 */           } else if (sortField.equals("nombre")) {
/*  70:103 */             sortFieldPorUsuario = "dimensionContable.nombre";
/*  71:    */           }
/*  72:107 */           List<String> listaCampos = new ArrayList();
/*  73:108 */           listaCampos.add("dimensionContable");
/*  74:109 */           listaCampos.add("entidadUsuario");
/*  75:    */           
/*  76:111 */           List<UsuarioDimensionContable> listaAux = ListaDimensionContableBean.this.servicioUsuarioDimensionContable.obtenerListaPorPagina(UsuarioDimensionContable.class, startIndex, pageSize, sortFieldPorUsuario, ordenar, filtersPorUsuario, listaCampos);
/*  77:113 */           for (UsuarioDimensionContable usuarioDimensionContable : listaAux) {
/*  78:114 */             lista.add(usuarioDimensionContable.getDimensionContable());
/*  79:    */           }
/*  80:116 */           ListaDimensionContableBean.this.listaDimensionContable.setRowCount(lista.size());
/*  81:    */         }
/*  82:119 */         if (lista.isEmpty())
/*  83:    */         {
/*  84:120 */           lista = ListaDimensionContableBean.this.servicioDimensionContable.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  85:121 */           ListaDimensionContableBean.this.listaDimensionContable.setRowCount(ListaDimensionContableBean.this.servicioDimensionContable.contarPorCriterio(filters));
/*  86:    */         }
/*  87:124 */         return lista;
/*  88:    */       }
/*  89:    */     };
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void seleccionarDimensionContableListener(DimensionContable dimensionContable)
/*  93:    */   {
/*  94:130 */     RequestContext.getCurrentInstance().closeDialog(dimensionContable);
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void listarDimensionContableListener(String numeroDimension)
/*  98:    */   {
/*  99:134 */     listarDimensionContableListener(numeroDimension, false);
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void listarDimensionContableListener(String numeroDimension, boolean indicadorSeleccionarTodo)
/* 103:    */   {
/* 104:138 */     listarDimensionContableListener(numeroDimension, indicadorSeleccionarTodo, false);
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void listarDimensionContableListener(String numeroDimension, boolean indicadorSeleccionarTodo, boolean porUsuario)
/* 108:    */   {
/* 109:142 */     setPorUsuario(porUsuario);
/* 110:143 */     setNumeroDimension(numeroDimension);
/* 111:144 */     setIndicadorSeleccionarTodo(indicadorSeleccionarTodo);
/* 112:    */     
/* 113:146 */     Map<String, Object> properties = new HashMap();
/* 114:147 */     properties.put("modal", Boolean.valueOf(true));
/* 115:148 */     properties.put("resizable", Boolean.valueOf(true));
/* 116:149 */     properties.put("draggable", Boolean.valueOf(true));
/* 117:150 */     properties.put("width", Integer.valueOf(800));
/* 118:151 */     properties.put("height", Integer.valueOf(400));
/* 119:152 */     RequestContext.getCurrentInstance().openDialog("/resources/componentes/seleccionarDimensionContable");
/* 120:    */   }
/* 121:    */   
/* 122:    */   public DimensionContable getDimensionContable()
/* 123:    */   {
/* 124:161 */     return this.dimensionContable;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void setDimensionContable(DimensionContable dimensionContable)
/* 128:    */   {
/* 129:171 */     this.dimensionContable = dimensionContable;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public LazyDataModel<DimensionContable> getListaDimensionContable()
/* 133:    */   {
/* 134:180 */     return this.listaDimensionContable;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void setListaDimensionContable(LazyDataModel<DimensionContable> listaDimensionContable)
/* 138:    */   {
/* 139:190 */     this.listaDimensionContable = listaDimensionContable;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public String getNumeroDimension()
/* 143:    */   {
/* 144:197 */     return this.numeroDimension;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void setNumeroDimension(String numeroDimension)
/* 148:    */   {
/* 149:205 */     this.numeroDimension = numeroDimension;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public boolean isIndicadorSeleccionarTodo()
/* 153:    */   {
/* 154:209 */     return this.indicadorSeleccionarTodo;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public void setIndicadorSeleccionarTodo(boolean indicadorSeleccionarTodo)
/* 158:    */   {
/* 159:213 */     this.indicadorSeleccionarTodo = indicadorSeleccionarTodo;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public boolean isPorUsuario()
/* 163:    */   {
/* 164:217 */     return this.porUsuario;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public void setPorUsuario(boolean porUsuario)
/* 168:    */   {
/* 169:221 */     this.porUsuario = porUsuario;
/* 170:    */   }
/* 171:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.contabilidad.configuracion.controller.ListaDimensionContableBean
 * JD-Core Version:    0.7.0.1
 */