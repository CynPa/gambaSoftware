/*   1:    */ package com.asinfo.as2.entities.mantenimiento;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.EntidadBase;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.List;
/*   7:    */ import javax.persistence.Column;
/*   8:    */ import javax.persistence.Entity;
/*   9:    */ import javax.persistence.FetchType;
/*  10:    */ import javax.persistence.GeneratedValue;
/*  11:    */ import javax.persistence.GenerationType;
/*  12:    */ import javax.persistence.Id;
/*  13:    */ import javax.persistence.JoinColumn;
/*  14:    */ import javax.persistence.ManyToOne;
/*  15:    */ import javax.persistence.OneToMany;
/*  16:    */ import javax.persistence.Table;
/*  17:    */ import javax.persistence.TableGenerator;
/*  18:    */ import javax.validation.constraints.NotNull;
/*  19:    */ import javax.validation.constraints.Size;
/*  20:    */ 
/*  21:    */ @Entity
/*  22:    */ @Table(name="plan_mantenimiento", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  23:    */ public class PlanMantenimiento
/*  24:    */   extends EntidadBase
/*  25:    */   implements Serializable
/*  26:    */ {
/*  27:    */   private static final long serialVersionUID = 1L;
/*  28:    */   @Id
/*  29:    */   @TableGenerator(name="plan_mantenimiento", initialValue=0, allocationSize=50)
/*  30:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="plan_mantenimiento")
/*  31:    */   @Column(name="id_plan_mantenimiento")
/*  32:    */   private int idPlanMantenimiento;
/*  33:    */   @Column(name="id_organizacion")
/*  34:    */   private int idOrganizacion;
/*  35:    */   @Column(name="id_sucursal")
/*  36:    */   private int idSucursal;
/*  37:    */   @Column(name="codigo", nullable=false, length=20)
/*  38:    */   @NotNull
/*  39:    */   @Size(min=1, max=20)
/*  40:    */   private String codigo;
/*  41:    */   @Column(name="nombre", nullable=false, length=100)
/*  42:    */   @NotNull
/*  43:    */   @Size(min=2, max=100)
/*  44:    */   private String nombre;
/*  45:    */   @Column(name="descripcion", length=200, nullable=true)
/*  46:    */   @Size(max=200)
/*  47:    */   private String descripcion;
/*  48:    */   @Column(name="predeterminado", nullable=false)
/*  49:    */   private boolean predeterminado;
/*  50:    */   @Column(name="activo", nullable=false)
/*  51:    */   private boolean activo;
/*  52:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="planMantenimiento")
/*  53: 72 */   private List<DetallePlanMantenimiento> listaDetallePlanMantenimiento = new ArrayList();
/*  54:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  55:    */   @JoinColumn(name="id_categoria_equipo", nullable=true)
/*  56:    */   private CategoriaEquipo categoriaEquipo;
/*  57:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  58:    */   @JoinColumn(name="id_subcategoria_equipo", nullable=true)
/*  59:    */   private SubcategoriaEquipo subcategoriaEquipo;
/*  60:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="planMantenimiento")
/*  61: 83 */   private List<PlanMantenimientoEquipo> listaPlanMantenimientoEquipo = new ArrayList();
/*  62:    */   
/*  63:    */   public int getId()
/*  64:    */   {
/*  65: 96 */     return this.idPlanMantenimiento;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public int getIdPlanMantenimiento()
/*  69:    */   {
/*  70:103 */     return this.idPlanMantenimiento;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void setIdPlanMantenimiento(int idPlanMantenimiento)
/*  74:    */   {
/*  75:111 */     this.idPlanMantenimiento = idPlanMantenimiento;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public int getIdOrganizacion()
/*  79:    */   {
/*  80:118 */     return this.idOrganizacion;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void setIdOrganizacion(int idOrganizacion)
/*  84:    */   {
/*  85:126 */     this.idOrganizacion = idOrganizacion;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public int getIdSucursal()
/*  89:    */   {
/*  90:133 */     return this.idSucursal;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void setIdSucursal(int idSucursal)
/*  94:    */   {
/*  95:141 */     this.idSucursal = idSucursal;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public String getCodigo()
/*  99:    */   {
/* 100:148 */     return this.codigo;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void setCodigo(String codigo)
/* 104:    */   {
/* 105:156 */     this.codigo = codigo;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public String getNombre()
/* 109:    */   {
/* 110:163 */     return this.nombre;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void setNombre(String nombre)
/* 114:    */   {
/* 115:171 */     this.nombre = nombre;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public String getDescripcion()
/* 119:    */   {
/* 120:178 */     return this.descripcion;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void setDescripcion(String descripcion)
/* 124:    */   {
/* 125:186 */     this.descripcion = descripcion;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public boolean isPredeterminado()
/* 129:    */   {
/* 130:193 */     return this.predeterminado;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void setPredeterminado(boolean predeterminado)
/* 134:    */   {
/* 135:201 */     this.predeterminado = predeterminado;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public boolean isActivo()
/* 139:    */   {
/* 140:208 */     return this.activo;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void setActivo(boolean activo)
/* 144:    */   {
/* 145:216 */     this.activo = activo;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public List<DetallePlanMantenimiento> getListaDetallePlanMantenimiento()
/* 149:    */   {
/* 150:223 */     return this.listaDetallePlanMantenimiento;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void setListaDetallePlanMantenimiento(List<DetallePlanMantenimiento> listaDetallePlanMantenimiento)
/* 154:    */   {
/* 155:231 */     this.listaDetallePlanMantenimiento = listaDetallePlanMantenimiento;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public CategoriaEquipo getCategoriaEquipo()
/* 159:    */   {
/* 160:235 */     return this.categoriaEquipo;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void setCategoriaEquipo(CategoriaEquipo categoriaEquipo)
/* 164:    */   {
/* 165:239 */     this.categoriaEquipo = categoriaEquipo;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public SubcategoriaEquipo getSubcategoriaEquipo()
/* 169:    */   {
/* 170:243 */     return this.subcategoriaEquipo;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public void setSubcategoriaEquipo(SubcategoriaEquipo subcategoriaEquipo)
/* 174:    */   {
/* 175:247 */     this.subcategoriaEquipo = subcategoriaEquipo;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public List<PlanMantenimientoEquipo> getListaPlanMantenimientoEquipo()
/* 179:    */   {
/* 180:251 */     return this.listaPlanMantenimientoEquipo;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public void setListaPlanMantenimientoEquipo(List<PlanMantenimientoEquipo> listaPlanMantenimientoEquipo)
/* 184:    */   {
/* 185:255 */     this.listaPlanMantenimientoEquipo = listaPlanMantenimientoEquipo;
/* 186:    */   }
/* 187:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.mantenimiento.PlanMantenimiento
 * JD-Core Version:    0.7.0.1
 */