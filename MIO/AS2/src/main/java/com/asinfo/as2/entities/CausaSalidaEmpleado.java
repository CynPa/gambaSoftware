/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import javax.persistence.Column;
/*   4:    */ import javax.persistence.Entity;
/*   5:    */ import javax.persistence.GeneratedValue;
/*   6:    */ import javax.persistence.GenerationType;
/*   7:    */ import javax.persistence.Id;
/*   8:    */ import javax.persistence.Table;
/*   9:    */ import javax.persistence.TableGenerator;
/*  10:    */ import javax.validation.constraints.NotNull;
/*  11:    */ import javax.validation.constraints.Size;
/*  12:    */ 
/*  13:    */ @Entity
/*  14:    */ @Table(name="causa_salida_empleado", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  15:    */ public class CausaSalidaEmpleado
/*  16:    */   extends EntidadBase
/*  17:    */ {
/*  18:    */   private static final long serialVersionUID = 3080437661251148955L;
/*  19:    */   @Column(name="id_organizacion", nullable=false)
/*  20:    */   private int idOrganizacion;
/*  21:    */   @Column(name="id_sucursal", nullable=false)
/*  22:    */   private int idSucursal;
/*  23:    */   @Id
/*  24:    */   @TableGenerator(name="causa_salida_empleado", initialValue=0, allocationSize=50)
/*  25:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="causa_salida_empleado")
/*  26:    */   @Column(name="id_causa_salida_empleado")
/*  27:    */   private int idCausaSalidaEmpleado;
/*  28:    */   @Column(name="codigo", nullable=false, length=10)
/*  29:    */   @Size(min=2, max=10)
/*  30:    */   @NotNull
/*  31:    */   private String codigo;
/*  32:    */   @Column(name="nombre", nullable=false, length=50)
/*  33:    */   @Size(min=2, max=50)
/*  34:    */   @NotNull
/*  35:    */   private String nombre;
/*  36:    */   @Column(name="descripcion", nullable=true, length=200)
/*  37:    */   @Size(max=200)
/*  38:    */   private String descripcion;
/*  39:    */   @Column(name="activo", nullable=false)
/*  40:    */   private boolean activo;
/*  41:    */   @Column(name="predeterminado", nullable=false)
/*  42:    */   private boolean predeterminado;
/*  43:    */   
/*  44:    */   public int getIdOrganizacion()
/*  45:    */   {
/*  46: 99 */     return this.idOrganizacion;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void setIdOrganizacion(int idOrganizacion)
/*  50:    */   {
/*  51:109 */     this.idOrganizacion = idOrganizacion;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public int getIdSucursal()
/*  55:    */   {
/*  56:118 */     return this.idSucursal;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void setIdSucursal(int idSucursal)
/*  60:    */   {
/*  61:128 */     this.idSucursal = idSucursal;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public int getIdCausaSalidaEmpleado()
/*  65:    */   {
/*  66:137 */     return this.idCausaSalidaEmpleado;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void setIdCausaSalidaEmpleado(int idCausaSalidaEmpleado)
/*  70:    */   {
/*  71:147 */     this.idCausaSalidaEmpleado = idCausaSalidaEmpleado;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public String getCodigo()
/*  75:    */   {
/*  76:156 */     return this.codigo;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void setCodigo(String codigo)
/*  80:    */   {
/*  81:166 */     this.codigo = codigo;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public String getNombre()
/*  85:    */   {
/*  86:175 */     return this.nombre;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void setNombre(String nombre)
/*  90:    */   {
/*  91:185 */     this.nombre = nombre;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public String getDescripcion()
/*  95:    */   {
/*  96:194 */     return this.descripcion;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void setDescripcion(String descripcion)
/* 100:    */   {
/* 101:204 */     this.descripcion = descripcion;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public boolean isActivo()
/* 105:    */   {
/* 106:213 */     return this.activo;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void setActivo(boolean activo)
/* 110:    */   {
/* 111:223 */     this.activo = activo;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public boolean isPredeterminado()
/* 115:    */   {
/* 116:232 */     return this.predeterminado;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void setPredeterminado(boolean predeterminado)
/* 120:    */   {
/* 121:242 */     this.predeterminado = predeterminado;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public int getId()
/* 125:    */   {
/* 126:252 */     return this.idCausaSalidaEmpleado;
/* 127:    */   }
/* 128:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.CausaSalidaEmpleado
 * JD-Core Version:    0.7.0.1
 */