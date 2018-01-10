/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.List;
/*   5:    */ import javax.persistence.Column;
/*   6:    */ import javax.persistence.Entity;
/*   7:    */ import javax.persistence.FetchType;
/*   8:    */ import javax.persistence.GeneratedValue;
/*   9:    */ import javax.persistence.GenerationType;
/*  10:    */ import javax.persistence.Id;
/*  11:    */ import javax.persistence.OneToMany;
/*  12:    */ import javax.persistence.Table;
/*  13:    */ import javax.persistence.TableGenerator;
/*  14:    */ import javax.validation.constraints.NotNull;
/*  15:    */ import javax.validation.constraints.Size;
/*  16:    */ 
/*  17:    */ @Entity
/*  18:    */ @Table(name="tipo_permiso_empleado", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"}), @javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "nombre"})})
/*  19:    */ public class TipoPermisoEmpleado
/*  20:    */   extends EntidadBase
/*  21:    */ {
/*  22:    */   private static final long serialVersionUID = -7800704729609284883L;
/*  23:    */   @Id
/*  24:    */   @TableGenerator(name="tipo_permiso_empleado", initialValue=0, allocationSize=50)
/*  25:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="tipo_permiso_empleado")
/*  26:    */   @Column(name="id_tipo_permiso_empleado")
/*  27:    */   private int idTipoPermisoEmpleado;
/*  28:    */   @Column(name="id_organizacion", nullable=false)
/*  29:    */   private int idOrganizacion;
/*  30:    */   @Column(name="id_sucursal", nullable=false)
/*  31:    */   private int idSucursal;
/*  32:    */   @Column(name="codigo", length=10, nullable=false)
/*  33:    */   @NotNull
/*  34:    */   @Size(min=2, max=10)
/*  35:    */   private String codigo;
/*  36:    */   @Column(name="nombre", length=50, nullable=false)
/*  37:    */   @NotNull
/*  38:    */   @Size(min=2, max=50)
/*  39:    */   private String nombre;
/*  40:    */   @Column(name="descripcion", length=200)
/*  41:    */   @Size(max=200)
/*  42:    */   private String descripcion;
/*  43:    */   @Column(name="activo", nullable=false)
/*  44:    */   private boolean activo;
/*  45:    */   @Column(name="predeterminado", nullable=false)
/*  46:    */   private boolean predeterminado;
/*  47:    */   @Column(name="indicador_cargo_vacacion", nullable=false)
/*  48:    */   private boolean indicadorCargoVacacion;
/*  49:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="tipoPermisoEmpleado")
/*  50: 85 */   private List<PermisoEmpleado> listaPermisoEmpleado = new ArrayList();
/*  51:    */   
/*  52:    */   public int getIdTipoPermisoEmpleado()
/*  53:    */   {
/*  54:106 */     return this.idTipoPermisoEmpleado;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void setIdTipoPermisoEmpleado(int idTipoPermisoEmpleado)
/*  58:    */   {
/*  59:116 */     this.idTipoPermisoEmpleado = idTipoPermisoEmpleado;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public int getIdOrganizacion()
/*  63:    */   {
/*  64:125 */     return this.idOrganizacion;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void setIdOrganizacion(int idOrganizacion)
/*  68:    */   {
/*  69:135 */     this.idOrganizacion = idOrganizacion;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public int getIdSucursal()
/*  73:    */   {
/*  74:144 */     return this.idSucursal;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void setIdSucursal(int idSucursal)
/*  78:    */   {
/*  79:154 */     this.idSucursal = idSucursal;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public String getCodigo()
/*  83:    */   {
/*  84:163 */     return this.codigo;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void setCodigo(String codigo)
/*  88:    */   {
/*  89:173 */     this.codigo = codigo;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public String getNombre()
/*  93:    */   {
/*  94:182 */     return this.nombre;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void setNombre(String nombre)
/*  98:    */   {
/*  99:192 */     this.nombre = nombre;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public String getDescripcion()
/* 103:    */   {
/* 104:201 */     return this.descripcion;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void setDescripcion(String descripcion)
/* 108:    */   {
/* 109:211 */     this.descripcion = descripcion;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public boolean isActivo()
/* 113:    */   {
/* 114:220 */     return this.activo;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void setActivo(boolean activo)
/* 118:    */   {
/* 119:230 */     this.activo = activo;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public boolean isPredeterminado()
/* 123:    */   {
/* 124:239 */     return this.predeterminado;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void setPredeterminado(boolean predeterminado)
/* 128:    */   {
/* 129:249 */     this.predeterminado = predeterminado;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public boolean isIndicadorCargoVacacion()
/* 133:    */   {
/* 134:258 */     return this.indicadorCargoVacacion;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void setIndicadorCargoVacacion(boolean indicadorCargoVacacion)
/* 138:    */   {
/* 139:268 */     this.indicadorCargoVacacion = indicadorCargoVacacion;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public List<PermisoEmpleado> getListaPermisoEmpleado()
/* 143:    */   {
/* 144:277 */     return this.listaPermisoEmpleado;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void setListaPermisoEmpleado(List<PermisoEmpleado> listaPermisoEmpleado)
/* 148:    */   {
/* 149:287 */     this.listaPermisoEmpleado = listaPermisoEmpleado;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public int getId()
/* 153:    */   {
/* 154:297 */     return this.idTipoPermisoEmpleado;
/* 155:    */   }
/* 156:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.TipoPermisoEmpleado
 * JD-Core Version:    0.7.0.1
 */