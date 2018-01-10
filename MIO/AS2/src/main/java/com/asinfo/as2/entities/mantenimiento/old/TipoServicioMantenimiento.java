/*   1:    */ package com.asinfo.as2.entities.mantenimiento.old;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.EntidadBase;
/*   4:    */ import javax.persistence.Column;
/*   5:    */ import javax.persistence.Entity;
/*   6:    */ import javax.persistence.GeneratedValue;
/*   7:    */ import javax.persistence.GenerationType;
/*   8:    */ import javax.persistence.Id;
/*   9:    */ import javax.persistence.Table;
/*  10:    */ import javax.persistence.TableGenerator;
/*  11:    */ import javax.validation.constraints.NotNull;
/*  12:    */ import javax.validation.constraints.Size;
/*  13:    */ 
/*  14:    */ @Entity
/*  15:    */ @Table(name="tipo_servicio_mantenimiento")
/*  16:    */ public class TipoServicioMantenimiento
/*  17:    */   extends EntidadBase
/*  18:    */ {
/*  19:    */   private static final long serialVersionUID = -7022282968690929131L;
/*  20:    */   @Id
/*  21:    */   @TableGenerator(name="tipo_servicio_mantenimiento", initialValue=0, allocationSize=50)
/*  22:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="tipo_servicio_mantenimiento")
/*  23:    */   @Column(name="id_tipo_servicio_mantenimiento")
/*  24:    */   private int idTipoServicioMantenimiento;
/*  25:    */   @Column(name="id_organizacion", nullable=false)
/*  26:    */   private int idOrganizacion;
/*  27:    */   @Column(name="id_sucursal", nullable=false)
/*  28:    */   private int idSucursal;
/*  29:    */   @Column(name="codigo", nullable=false, length=10)
/*  30:    */   @NotNull
/*  31:    */   @Size(min=2, max=10)
/*  32:    */   private String codigo;
/*  33:    */   @Column(name="nombre", nullable=false, length=50)
/*  34:    */   @NotNull
/*  35:    */   @Size(min=2, max=50)
/*  36:    */   private String nombre;
/*  37:    */   @Column(name="descripcion", length=200)
/*  38:    */   @Size(max=200)
/*  39:    */   private String descripcion;
/*  40:    */   @Column(name="activo", nullable=false)
/*  41:    */   private boolean activo;
/*  42:    */   @Column(name="predeterminado", nullable=false)
/*  43:    */   private boolean predeterminado;
/*  44:    */   
/*  45:    */   public TipoServicioMantenimiento() {}
/*  46:    */   
/*  47:    */   public TipoServicioMantenimiento(int idTipoServicioMantenimiento, String nombre, String codigo)
/*  48:    */   {
/*  49: 88 */     this.idTipoServicioMantenimiento = idTipoServicioMantenimiento;
/*  50: 89 */     this.nombre = nombre;
/*  51: 90 */     this.codigo = codigo;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public int getIdTipoServicioMantenimiento()
/*  55:    */   {
/*  56: 99 */     return this.idTipoServicioMantenimiento;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void setIdTipoServicioMantenimiento(int idTipoServicioMantenimiento)
/*  60:    */   {
/*  61:109 */     this.idTipoServicioMantenimiento = idTipoServicioMantenimiento;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public int getIdOrganizacion()
/*  65:    */   {
/*  66:118 */     return this.idOrganizacion;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void setIdOrganizacion(int idOrganizacion)
/*  70:    */   {
/*  71:128 */     this.idOrganizacion = idOrganizacion;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public int getIdSucursal()
/*  75:    */   {
/*  76:137 */     return this.idSucursal;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void setIdSucursal(int idSucursal)
/*  80:    */   {
/*  81:147 */     this.idSucursal = idSucursal;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public String getCodigo()
/*  85:    */   {
/*  86:156 */     return this.codigo;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void setCodigo(String codigo)
/*  90:    */   {
/*  91:166 */     this.codigo = codigo;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public String getNombre()
/*  95:    */   {
/*  96:175 */     return this.nombre;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void setNombre(String nombre)
/* 100:    */   {
/* 101:185 */     this.nombre = nombre;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public String getDescripcion()
/* 105:    */   {
/* 106:194 */     return this.descripcion;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void setDescripcion(String descripcion)
/* 110:    */   {
/* 111:204 */     this.descripcion = descripcion;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public boolean isActivo()
/* 115:    */   {
/* 116:213 */     return this.activo;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void setActivo(boolean activo)
/* 120:    */   {
/* 121:223 */     this.activo = activo;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public boolean isPredeterminado()
/* 125:    */   {
/* 126:232 */     return this.predeterminado;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void setPredeterminado(boolean predeterminado)
/* 130:    */   {
/* 131:242 */     this.predeterminado = predeterminado;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public int getId()
/* 135:    */   {
/* 136:247 */     return this.idTipoServicioMantenimiento;
/* 137:    */   }
/* 138:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.mantenimiento.old.TipoServicioMantenimiento
 * JD-Core Version:    0.7.0.1
 */