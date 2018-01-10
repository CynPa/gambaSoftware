/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import javax.persistence.Column;
/*   4:    */ import javax.persistence.Entity;
/*   5:    */ import javax.persistence.FetchType;
/*   6:    */ import javax.persistence.GeneratedValue;
/*   7:    */ import javax.persistence.GenerationType;
/*   8:    */ import javax.persistence.Id;
/*   9:    */ import javax.persistence.JoinColumn;
/*  10:    */ import javax.persistence.ManyToOne;
/*  11:    */ import javax.persistence.Table;
/*  12:    */ import javax.persistence.TableGenerator;
/*  13:    */ import javax.validation.constraints.Size;
/*  14:    */ 
/*  15:    */ @Entity
/*  16:    */ @Table(name="ubicacion_activo", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  17:    */ public class UbicacionActivo
/*  18:    */   extends EntidadBase
/*  19:    */ {
/*  20:    */   private static final long serialVersionUID = 1L;
/*  21:    */   @Id
/*  22:    */   @TableGenerator(name="ubicacion_activo", initialValue=0, allocationSize=50)
/*  23:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="ubicacion_activo")
/*  24:    */   @Column(name="id_ubicacion_activo")
/*  25:    */   private int idUbicacionActivo;
/*  26:    */   @Column(name="id_organizacion", nullable=false)
/*  27:    */   private int idOrganizacion;
/*  28:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  29:    */   @JoinColumn(name="id_sucursal", nullable=false)
/*  30:    */   private Sucursal sucursal;
/*  31:    */   @Column(name="codigo", nullable=false, length=10)
/*  32:    */   @Size(min=2, max=10)
/*  33:    */   private String codigo;
/*  34:    */   @Column(name="nombre", nullable=false, length=50)
/*  35:    */   @Size(min=2, max=50)
/*  36:    */   private String nombre;
/*  37:    */   @Column(name="descripcion", nullable=true, length=200)
/*  38:    */   @Size(max=200)
/*  39:    */   private String descripcion;
/*  40:    */   @Column(name="activo", nullable=false)
/*  41:    */   private boolean activo;
/*  42:    */   @Column(name="predeterminado", nullable=false)
/*  43:    */   private boolean predeterminado;
/*  44:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  45:    */   @JoinColumn(name="id_departamento", nullable=false)
/*  46:    */   private Departamento departamento;
/*  47:    */   
/*  48:    */   public UbicacionActivo() {}
/*  49:    */   
/*  50:    */   public UbicacionActivo(int idUbicacionActivo, String codigo, String nombre)
/*  51:    */   {
/*  52:102 */     this.idUbicacionActivo = idUbicacionActivo;
/*  53:103 */     this.nombre = nombre;
/*  54:104 */     this.codigo = codigo;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public int getIdUbicacionActivo()
/*  58:    */   {
/*  59:117 */     return this.idUbicacionActivo;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void setIdUbicacionActivo(int idUbicacionActivo)
/*  63:    */   {
/*  64:127 */     this.idUbicacionActivo = idUbicacionActivo;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public int getIdOrganizacion()
/*  68:    */   {
/*  69:136 */     return this.idOrganizacion;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void setIdOrganizacion(int idOrganizacion)
/*  73:    */   {
/*  74:146 */     this.idOrganizacion = idOrganizacion;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public String getNombre()
/*  78:    */   {
/*  79:155 */     return this.nombre;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void setNombre(String nombre)
/*  83:    */   {
/*  84:165 */     this.nombre = nombre;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public String getCodigo()
/*  88:    */   {
/*  89:174 */     return this.codigo;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void setCodigo(String codigo)
/*  93:    */   {
/*  94:184 */     this.codigo = codigo;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public String getDescripcion()
/*  98:    */   {
/*  99:193 */     return this.descripcion;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void setDescripcion(String descripcion)
/* 103:    */   {
/* 104:203 */     this.descripcion = descripcion;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public boolean isActivo()
/* 108:    */   {
/* 109:212 */     return this.activo;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void setActivo(boolean activo)
/* 113:    */   {
/* 114:222 */     this.activo = activo;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public boolean isPredeterminado()
/* 118:    */   {
/* 119:231 */     return this.predeterminado;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void setPredeterminado(boolean predeterminado)
/* 123:    */   {
/* 124:241 */     this.predeterminado = predeterminado;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public Departamento getDepartamento()
/* 128:    */   {
/* 129:250 */     return this.departamento;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void setDepartamento(Departamento departamento)
/* 133:    */   {
/* 134:260 */     this.departamento = departamento;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public int getId()
/* 138:    */   {
/* 139:270 */     return this.idUbicacionActivo;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public Sucursal getSucursal()
/* 143:    */   {
/* 144:274 */     return this.sucursal;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void setSucursal(Sucursal sucursal)
/* 148:    */   {
/* 149:278 */     this.sucursal = sucursal;
/* 150:    */   }
/* 151:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.UbicacionActivo
 * JD-Core Version:    0.7.0.1
 */