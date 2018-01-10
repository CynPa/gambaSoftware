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
/*  14:    */ @Table(name="estado_civil", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  15:    */ public class EstadoCivil
/*  16:    */   extends EntidadBase
/*  17:    */ {
/*  18:    */   private static final long serialVersionUID = -3276175199847290817L;
/*  19:    */   @Column(name="id_organizacion", nullable=false)
/*  20:    */   private int idOrganizacion;
/*  21:    */   @Column(name="id_sucursal", nullable=false)
/*  22:    */   private int idSucursal;
/*  23:    */   @Id
/*  24:    */   @TableGenerator(name="estado_civil", initialValue=0, allocationSize=50)
/*  25:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="estado_civil")
/*  26:    */   @Column(name="id_estado_civil")
/*  27:    */   private int idEstadoCivil;
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
/*  44:    */   public EstadoCivil() {}
/*  45:    */   
/*  46:    */   public EstadoCivil(int idEstadoCivil, String codigo, String nombre)
/*  47:    */   {
/*  48: 91 */     this.idEstadoCivil = idEstadoCivil;
/*  49: 92 */     this.codigo = codigo;
/*  50: 93 */     this.nombre = nombre;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public int getIdOrganizacion()
/*  54:    */   {
/*  55:105 */     return this.idOrganizacion;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void setIdOrganizacion(int idOrganizacion)
/*  59:    */   {
/*  60:115 */     this.idOrganizacion = idOrganizacion;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public int getIdSucursal()
/*  64:    */   {
/*  65:124 */     return this.idSucursal;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void setIdSucursal(int idSucursal)
/*  69:    */   {
/*  70:134 */     this.idSucursal = idSucursal;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public int getIdEstadoCivil()
/*  74:    */   {
/*  75:143 */     return this.idEstadoCivil;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void setIdEstadoCivil(int idEstadoCivil)
/*  79:    */   {
/*  80:153 */     this.idEstadoCivil = idEstadoCivil;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public String getCodigo()
/*  84:    */   {
/*  85:162 */     return this.codigo;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void setCodigo(String codigo)
/*  89:    */   {
/*  90:172 */     this.codigo = codigo;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public String getNombre()
/*  94:    */   {
/*  95:181 */     return this.nombre;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void setNombre(String nombre)
/*  99:    */   {
/* 100:191 */     this.nombre = nombre;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public String getDescripcion()
/* 104:    */   {
/* 105:200 */     return this.descripcion;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void setDescripcion(String descripcion)
/* 109:    */   {
/* 110:210 */     this.descripcion = descripcion;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public boolean isActivo()
/* 114:    */   {
/* 115:219 */     return this.activo;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void setActivo(boolean activo)
/* 119:    */   {
/* 120:229 */     this.activo = activo;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public boolean isPredeterminado()
/* 124:    */   {
/* 125:238 */     return this.predeterminado;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void setPredeterminado(boolean predeterminado)
/* 129:    */   {
/* 130:248 */     this.predeterminado = predeterminado;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public int getId()
/* 134:    */   {
/* 135:258 */     return this.idEstadoCivil;
/* 136:    */   }
/* 137:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.EstadoCivil
 * JD-Core Version:    0.7.0.1
 */